package com.esgi.pa.api.resources;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esgi.pa.api.dtos.responses.GetlobbiesResponse;
import com.esgi.pa.api.mappers.LobbyMapper;
import com.esgi.pa.api.mappers.UserMapper;
import com.esgi.pa.domain.exceptions.TechnicalException;
import com.esgi.pa.domain.services.UserService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Api(tags = "User API")
public class UserResource {
    private final UserService userService;

    @GetMapping("name/{name}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String name) {
            return ResponseEntity.ok(UserMapper.toGetUserResponse(
                            userService.getByName(name)));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getUserById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(
                    UserMapper.toGetUserResponse(
                            userService.getById(id)));
        } catch (TechnicalException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMap());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("An error occurred while processing the request.");
        }
    }

    @GetMapping("{id}/lobbies")
    public ResponseEntity<?> getLobbies(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(
                    new GetlobbiesResponse(
                            LobbyMapper.toGetlobbyResponse(
                                    userService.getLobbiesByUserId(id))));
        } catch (TechnicalException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMap());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("An error occurred while processing the request.");
        }
    }
}
