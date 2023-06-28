package com.esgi.pa.domain.services;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.esgi.pa.domain.entities.Game;
import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.server.adapter.GameAdapter;

import lombok.RequiredArgsConstructor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.script.*;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameAdapter gameAdapter;
    private static Process process;
    private static BufferedWriter writer;
    private static BufferedReader reader;
    private static StringBuilder outputBuilder;

    private static boolean isNewInstance = true; // Flag to track if a new instance needs to be created

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
        String fileName = saveFile(gameFiles);
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

    private String saveFile(MultipartFile file) throws IOException {
        // Get the file name
        String fileName = file.getOriginalFilename();
        // Set the file path where you want to save the file
        String filePath = "src/main/resources/files/" + fileName;

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
            return runScriptPython(lobby.getGame().getGameFiles(), jsonData);
        } else if ("js".equals(extension)) {
            return runScriptJavaScript(lobby.getGame().getGameFiles(), jsonData);
        } else {
            return "The file is not a Python file.";
        }
    }

    public String runScriptPython(String fileName, String jsonData) {
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
                createNewInstance("python", "src/main/resources/files/"+fileName);
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
            return output;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "{ \"error\": \"Erreur lors de l'exécution du script Python\" }";
        }
    }

    private void createNewInstance( String type, String fileName) throws IOException {
        // Execute the Python script as a process
        ProcessBuilder pb = new ProcessBuilder(type,fileName);
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
    public String runScriptJavaScript(String fileName, String jsonData) {
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
                createNewInstance("node", "src/main/resources/files/"+fileName);
            }

            // Read the JSON data as a Map
            Map<String, Object> dataMap = objectMapper.readValue(jsonData, new TypeReference<Map<String, Object>>() {});

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
            return output;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "{ \"error\": \"Erreur lors de l'exécution du script javascript\" }";
        }
    }
}
