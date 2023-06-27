package com.esgi.pa.domain.services;

import java.io.*;

public class MorpionRunner {
    private static Process process;
    private static BufferedWriter writer;
    private static BufferedReader reader;

    public static void initialize() throws IOException {
        ProcessBuilder pb = new ProcessBuilder("python", "src/main/resources/files/morpion.py.py");
        pb.redirectErrorStream(true);
        process = pb.start();
        writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
        reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    }

    public static String execute(String jsonData) throws IOException, InterruptedException {
        writer.write(jsonData);
        writer.newLine();
        writer.flush();

        StringBuilder output = new StringBuilder();
        String line;

        boolean skipLines = false;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("Traceback") || line.startsWith("File")) {
                skipLines = true;
            }
            if (!skipLines) {
                output.append(line);
            }
        }

        return output.toString();
    }

    public static void close() throws IOException, InterruptedException {
        writer.close();
        reader.close();
        process.waitFor();
    }
}
