package com.esgi.pa.api.resources;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/morpion")
public class EngineRessource {

    @PostMapping(value = "/morpion", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String runMorpion(@RequestBody String jsonData) {
        try {
            // Exécuter le script Python en tant que processus
            ProcessBuilder pb = new ProcessBuilder("python", "src/main/resources/files/morpion.py.py");
            pb.redirectErrorStream(true);
            Process process = pb.start();

            // Obtenir les flux d'entrée/sortie du processus Python
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            // Envoyer les données JSON à l'entrée du script Python
            writer.write(jsonData);
            writer.newLine();
            writer.flush();
            writer.close();

            // Lire la réponse JSON renvoyée par le script Python depuis la sortie
            StringBuilder output = new StringBuilder();
            String line;

            // Ignorer les lignes commençant par "Traceback" ou "File"
            boolean skipLines = false;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Traceback") || line.startsWith("File")) {
                    skipLines = true;
                }
                if (!skipLines) {
                    output.append(line);
                }
            }
            reader.close();

            // Attendre que le processus se termine
            process.waitFor();

            // Retourner la réponse JSON renvoyée par le script Python
            return output.toString();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "{ \"error\": \"Erreur lors de l'exécution du script Python\" }";
        }
    }
}
