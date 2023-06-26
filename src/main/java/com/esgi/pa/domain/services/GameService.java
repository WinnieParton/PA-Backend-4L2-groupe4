package com.esgi.pa.domain.services;

import com.esgi.pa.domain.entities.Game;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.server.adapter.GameAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameAdapter gameAdapter;

    public Game getById(Long gameId) throws TechnicalNotFoundException {
        return gameAdapter.findById(gameId)
                .orElseThrow(
                        () -> new TechnicalNotFoundException(HttpStatus.NOT_FOUND, "Cannot find game with id : " + gameId));
    }

    public Game createGame(String name, String description, MultipartFile  gameFiles, String miniature, int minPlayers, int maxPlayers) throws TechnicalFoundException, IOException {
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

}
