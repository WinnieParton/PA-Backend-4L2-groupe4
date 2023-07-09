package com.esgi.pa.domain.services;

import com.esgi.pa.domain.entities.Game;
import com.esgi.pa.domain.entities.Lobby;
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
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

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
    private final ResourceLoader resourceLoader;
    public Game getById(Long gameId) throws TechnicalNotFoundException {
        return gameAdapter.findById(gameId)
            .orElseThrow(
                () -> new TechnicalNotFoundException(HttpStatus.NOT_FOUND,
                    "Cannot find game with id : " + gameId));
    }

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

    public String saveFile(MultipartFile file, String fileName) throws IOException {
        // Set the file path where you want to save the file
        String filePath = UPLOAD_DIR + fileName;

        // Convert the MultipartFile to a byte array
        byte[] fileBytes = file.getBytes();

        // Save the file to the target location
        FileCopyUtils.copy(fileBytes, new File(filePath));

        return fileName;
    }

    public List<Game> findAll() {
        return gameAdapter.findAll();
    }

    public String runEngine(Lobby lobby, String jsonData) {
        String extension = FilenameUtils.getExtension(lobby.getGame().getGameFiles());

        if ("py".equals(extension)) {
            return runScriptPython(lobby, jsonData);
        } else if ("js".equals(extension)) {
            return runScriptJavaScript(lobby, jsonData);
        } else {
            return "The file is not a Python file.";
        }
    }

    public String runScriptPython(Lobby lobby, String jsonData) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // Check if jsonData is equal to {"init":{"players":2}}
            JsonNode jsonNode = objectMapper.readTree(jsonData);
            JsonNode initNode = jsonNode.get("init");
            if (initNode != null && initNode.toString().equals("{\"players\":2}")) {
                isNewInstance = true;
            }
            if (isNewInstance || process == null || writer == null || reader == null) {
                // Create a new instance only if jsonData matches the expected JSON structure
                String pythonCommand = getPythonCommand();
                  if (pythonCommand == null) {
                      throw new IOException("Python or Python3 not found in the environment");
                  }
                createNewInstance(pythonCommand, UPLOAD_DIR + lobby.getGame().getGameFiles());
            }
            // Send the JSON data to the input of the Python script
            writer.write(jsonData);
            writer.newLine();
            writer.flush();

            // Wait for a brief moment to allow the output to be captured
            Thread.sleep(500);

            // Retirer les caractères null et autres caractères indésirables
            String output = outputBuilder.toString().replaceAll("\\p{Cntrl}", "");

            // Use ObjectWriter to serialize the output with indentation
            ObjectWriter writer = objectMapper.writer().with(SerializationFeature.INDENT_OUTPUT);
            output = writer.writeValueAsString(objectMapper.readTree(output));

            // Clear the outputBuilder for the next request
            outputBuilder.setLength(0);

            // Return the output as the response
            moveService.saveGameState(lobby, output);
            return output;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "{ \"error\": \"Erreur lors de l'exécution du script Python\" }";
        }
    }

    private void createNewInstance(String type, String fileName) throws IOException {
        // Execute the Python script as a process
        ProcessBuilder pb = new ProcessBuilder(type, fileName);
        pb.redirectErrorStream(true);
        process = pb.start();

        // Get the input/output streams of the Python process
        writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
        reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        // Initialize the outputBuilder
        outputBuilder = new StringBuilder();
        // Start a separate thread to continuously read and store the output from the Python process
        Thread outputThread = new Thread(() -> {
            try {
                String line;
                // Ignorer les lignes commençant par "Traceback" ou "File"
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

        isNewInstance = false; // Set the flag to false indicating that a new instance is created

    }

    public String runScriptJavaScript(Lobby lobby, String jsonData) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // Check if jsonData is equal to {"init":{"players":2}}
            JsonNode jsonNode = objectMapper.readTree(jsonData);
            JsonNode initNode = jsonNode.get("init");
            if (initNode != null && initNode.toString().equals("{\"players\":2}")) {
                isNewInstance = true;
            }
            if (isNewInstance || process == null || writer == null || reader == null) {
                // Create a new instance only if jsonData matches the expected JSON structure
                createNewInstance("node", UPLOAD_DIR + lobby.getGame().getGameFiles());
            }

            // Read the JSON data as a Map
            Map<String, Object> dataMap = objectMapper.readValue(jsonData, new TypeReference<Map<String, Object>>() {
            });

            // Convert the Map back to JSON string in a single-line format
            String formattedJsonData = objectMapper.writeValueAsString(dataMap);

            // Send the JSON data to the input of the Python script
            writer.write(formattedJsonData);
            writer.newLine();
            writer.flush();

            // Wait for a brief moment to allow the output to be captured
            Thread.sleep(500);

            // Retrieve the output from the JS process
            String output = outputBuilder.toString().trim();

            // Clear the outputBuilder for the next request
            outputBuilder.setLength(0);

            // Return the output as the response
            moveService.saveGameState(lobby, output);
            return output;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "{ \"error\": \"Erreur lors de l'exécution du script javascript\" }";
        }
    }

    public String getLanguageFromExtension(String fileName ) {
        String extension = FilenameUtils.getExtension(fileName);
        switch (extension.toLowerCase()) {
            case "py":
                return "Python";
            case "js":
                return "JavaScript";
            case "java":
                return "Java";
            default:
                return "Unknown";
        }
    }

    public String getFileContent(String fileName) throws IOException {
        Resource resource = new ClassPathResource("files/" + fileName);
        byte[] fileBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
        return new String(fileBytes, StandardCharsets.UTF_8);
    }
    
    private String getPythonCommand() {
    // Try executing "python3" command first
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

    // If "python3" command failed or not found, try "python" command
    try {
        ProcessBuilder pb = new ProcessBuilder("python", "--version");
        Process process = pb.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = reader.readLine();
        if (line != null && line.startsWith("Python 2")) {
            return "python";
        }
    } catch (IOException e) {
        // Ignore exception
    }

    return null; // Python or Python3 not found
}
}
