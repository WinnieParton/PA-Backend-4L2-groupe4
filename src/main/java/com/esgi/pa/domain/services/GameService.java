package com.esgi.pa.domain.services;

import com.esgi.pa.domain.entities.Game;
import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.entities.Move;
import com.esgi.pa.domain.enums.ActionEnum;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.server.adapter.GameAdapter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Service de gestion des jeux
 */
@Service
@RequiredArgsConstructor
public class GameService {

    private final GameAdapter gameAdapter;
    private final MoveService moveService;
    private static Process process;
    private static BufferedWriter writer;
    private static BufferedReader reader;
    private static StringBuilder outputBuilder;
    private static boolean isNewInstance = true; // Flag to track if a new instance needs to be created
    private static final String UPLOAD_DIR = "src/main/resources/files/";

    /**
     * Récupère les données relative à un jeu par son id numérique
     *
     * @param gameId id numérique du jeu
     * @return les données relative au jeu
     * @throws TechnicalNotFoundException si le jeu n'est pas trouvé
     */
    public Game getById(Long gameId) throws TechnicalNotFoundException {
        return gameAdapter.findById(gameId)
            .orElseThrow(
                () -> new TechnicalNotFoundException(HttpStatus.NOT_FOUND,
                    "Cannot find game with id : " + gameId));
    }

    /**
     * Crée une entité jeu
     *
     * @param name        nom du jeu
     * @param description description du jeu
     * @param gameFiles   lien des fichiers de jeu
     * @param miniature   lien vers l'image miniature du jeu
     * @param minPlayers  nombre minimum de joueurs
     * @param maxPlayers  nombre maximum de joueurs
     * @return la nouvelle entité jeu
     * @throws TechnicalFoundException si le jeu existe déjà
     * @throws IOException             si problème lors de l'enregistrement des fichiers de jeu
     */
    public Game createGame(String name, String description, MultipartFile gameFiles, String miniature, int minPlayers,
                           int maxPlayers) throws TechnicalFoundException, IOException {
        if (gameAdapter.findByName(name))
            throw new TechnicalFoundException("A game using this name already exist : " + name);
        String fileName = saveFile(gameFiles, gameFiles.getOriginalFilename());
        return gameAdapter.save(
            Game.builder()
                .name(name)
                .description(description)
                .gameFiles(fileName)
                .miniature(miniature)
                .minPlayers(minPlayers)
                .maxPlayers(maxPlayers)
                .build());
    }

    /**
     * Sauvegarde les fichiers de jeu
     *
     * @param file     fichiers à enregistrer
     * @param fileName nom du fichier
     * @return nom du fichier
     * @throws IOException si erreur lors de l'enregistrement
     */
    public String saveFile(MultipartFile file, String fileName) throws IOException {
        String filePath = UPLOAD_DIR + fileName;
        byte[] fileBytes = file.getBytes();
        FileCopyUtils.copy(fileBytes, new File(filePath));
        return fileName;
    }

    /**
     * Récupère l'ensemble des jeux
     *
     * @return la liste des jeux
     */
    public List<Game> findAll() {
        return gameAdapter.findAll();
    }

    /**
     * Lance le moteur de jeu
     *
     * @param lobby    id du lobby
     * @param jsonData les données d'entrée pour le moteur de jeu
     * @return le retour du moteur de jeu
     */
    public String runEngine(Lobby lobby, String jsonData) {
        moveService.saveGameState(lobby, jsonData, ActionEnum.INPUT);
        moveService.verifyRollback();
        String extension = FilenameUtils.getExtension(lobby.getGame().getGameFiles());
        String output ="";
        List<Move> listLasMove = moveService.findListLastMoveInput(lobby);
        if ("py".equals(extension)) {
            for (Move move : listLasMove) {
                output = runScriptPython(lobby, move.getGameState());
            };
            closePythonInstance();
        } else if ("js".equals(extension)) {
            output = runScriptJavaScript(lobby, jsonData);
        } else {
            output = "Langage non supporté";
        }
        if(!Objects.equals(output, "null") && !output.contains("Erreur lors de l'exécution"))
            moveService.saveGameState(lobby, output, ActionEnum.OUTPUT);
        return output;
    }
    public String runEngineRollback(Lobby lobby,  List<Move> listLasMove) {
        moveService.verifyRollback();
        String extension = FilenameUtils.getExtension(lobby.getGame().getGameFiles());
        String output ="";
        if ("py".equals(extension)) {
            for (Move move : listLasMove) {
                output = runScriptPython(lobby, move.getGameState());
            };
            closePythonInstance();
        } else if ("js".equals(extension)) {
            output = runScriptJavaScript(lobby, "");
        } else {
            output = "Langage non supporté";
        }

        return output;
    }
    /**
     * Methode pour fermer l'instance
     */
    public void closePythonInstance() {
        if (process != null) {
            process.destroy();
            process = null;
            writer = null;
            reader = null;
            isNewInstance = true;
        }
    }

    /**
     * Run un fichier python
     *
     * @param lobby    lobby dans lequel le jeu est en cours
     * @param jsonData données en entrée
     * @return le nouvel état du jeu
     */
    public String runScriptPython(Lobby lobby, String jsonData) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonData);
            JsonNode initNode = jsonNode.get("init");
            if (initNode != null && initNode.toString().equals("{\"players\":2}")) {
                isNewInstance = true;
            }
            if (isNewInstance || process == null || writer == null || reader == null) {
                String pythonCommand = getPythonCommand();
                if (pythonCommand == null) {
                    throw new IOException("Python or Python3 not found in the environment");
                }
                createNewInstance(pythonCommand, UPLOAD_DIR + lobby.getGame().getGameFiles());
            }
            writer.write(jsonData);
            writer.newLine();
            writer.flush();

            Thread.sleep(500);

            String output = outputBuilder.toString().replaceAll("\\p{Cntrl}", "");

            ObjectWriter writer = objectMapper.writer().with(SerializationFeature.INDENT_OUTPUT);
            output = writer.writeValueAsString(objectMapper.readTree(output));

            outputBuilder.setLength(0);

            return output;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "{ \"error\": \"Erreur lors de l'exécution du script Python\" }";
        }
    }

    /**
     * Créer une nouvelle instance de jeu
     *
     * @param type     type d'instance à lancer
     * @param fileName nom du fichier
     * @throws IOException si erreur lors de la création d'une instance
     */
    private void createNewInstance(String type, String fileName) throws IOException {
        ProcessBuilder pb = new ProcessBuilder(type, fileName);
        pb.redirectErrorStream(true);
        process = pb.start();

        writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
        reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        outputBuilder = new StringBuilder();
        Thread outputThread = new Thread(() -> {
            try {
                String line;
                boolean skipLines = false;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("Traceback") || line.startsWith("File")) {
                        skipLines = true;
                    }
                    if (!skipLines) {
                        outputBuilder.append(line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        outputThread.start();
        isNewInstance = false;
    }

    /**
     * Run un fichier javascript
     *
     * @param lobby    lobby dans lequel le jeu est en cours
     * @param jsonData données en entrée
     * @return nouvel état du jeu
     */
    public String runScriptJavaScript(Lobby lobby, String jsonData) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonData);
            JsonNode initNode = jsonNode.get("init");
            if (initNode != null && initNode.toString().equals("{\"players\":2}")) {
                isNewInstance = true;
            }
            if (isNewInstance || process == null || writer == null || reader == null) {
                createNewInstance("node", UPLOAD_DIR + lobby.getGame().getGameFiles());
            }

            Map<String, Object> dataMap = objectMapper.readValue(jsonData, new TypeReference<>() {
            });
            String formattedJsonData = objectMapper.writeValueAsString(dataMap);

            writer.write(formattedJsonData);
            writer.newLine();
            writer.flush();

            Thread.sleep(500);

            String output = outputBuilder.toString().replaceAll("\\p{Cntrl}", "");

            ObjectWriter writer = objectMapper.writer().with(SerializationFeature.INDENT_OUTPUT);
            output = writer.writeValueAsString(objectMapper.readTree(output));

            outputBuilder.setLength(0);

            return output;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "{ \"error\": \"Erreur lors de l'exécution du script javascript\" }";
        }
    }

    /**
     * Récupère le langage en fonction de l'extension d'un fichier
     *
     * @param fileName nom du fichier
     * @return le nom du langage de programmation
     */
    public String getLanguageFromExtension(String fileName) {
        String extension = FilenameUtils.getExtension(fileName);
        return switch (extension.toLowerCase()) {
            case "py" -> "Python";
            case "js" -> "JavaScript";
            case "java" -> "Java";
            default -> "Unknown";
        };
    }

    /**
     * Récupère le fichier de jeu
     *
     * @param fileName nom du fichier de jeu
     * @return le fichier de jeu
     * @throws IOException si problème lors de la récupération du fichier
     */
    public String getFileContent(String fileName) throws IOException {
        File file = new File("src/main/resources/files/" + fileName);
        byte[] fileBytes = Files.readAllBytes(file.toPath());
        return new String(fileBytes, StandardCharsets.UTF_8);
    }

    /**
     * Permet de vérfié l'existence du package python3 sur l'environnement
     *
     * @return le nom de package python disponible sur l'environnement
     */
    private String getPythonCommand() {
        try {
            ProcessBuilder pb = new ProcessBuilder("python3", "--version");
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            if (line != null && line.startsWith("Python 3")) {
                return "python3";
            }
        } catch (IOException e) {
            // Ignore exception
        }

        try {
            ProcessBuilder pb = new ProcessBuilder("python", "--version");
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            if (line != null && (line.startsWith("Python 2") || line.startsWith("Python 3"))) {
                return "python";
            }
        } catch (IOException e) {
            // Ignore exception
        }

        return null;
    }
}
