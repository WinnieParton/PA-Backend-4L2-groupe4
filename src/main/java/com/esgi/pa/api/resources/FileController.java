package com.esgi.pa.api.resources;

import com.esgi.pa.domain.services.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

  private static final String UPLOAD_DIR = "src/main/resources/files/";
  private final GameService gameService;

  @PostMapping(
    value = "/upload",
    consumes = MediaType.MULTIPART_FORM_DATA_VALUE
  )
  public String uploadFile(MultipartFile audio) {
    try {
      String fileName =
        UUID.randomUUID().toString() + "_" + audio.getOriginalFilename();
      return gameService.saveFile(audio, fileName);
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException("Failed to upload file");
    }
  }

  @GetMapping("/download/{fileName}")
  public ResponseEntity<byte[]> downloadFile(@PathVariable String fileName) {
    try {
      Path filePath = Paths.get(UPLOAD_DIR + fileName);
      Resource resource = new UrlResource(filePath.toUri());

      if (resource.exists()) {
        byte[] fileBytes = Files.readAllBytes(filePath);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(
          MediaType.valueOf(MediaType.APPLICATION_OCTET_STREAM_VALUE)
        );
        headers.setContentDisposition(
          ContentDisposition
            .attachment()
            .filename(resource.getFilename())
            .build()
        );
        headers.setContentLength(fileBytes.length);

        return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
      } else {
        return ResponseEntity.notFound().build();
      }
    } catch (IOException e) {
      throw new RuntimeException("File not found", e);
    }
  }
}
