package com.esgi.pa.api.resources;

import com.esgi.pa.api.dtos.LoginDto;
import com.esgi.pa.api.dtos.requests.CreateUserRequest;
import com.esgi.pa.api.dtos.requests.UserLoginRequest;
import com.esgi.pa.api.mappers.LobbyMapper;
import com.esgi.pa.api.mappers.UserMapper;
import com.esgi.pa.domain.exceptions.FunctionalException;
import com.esgi.pa.domain.exceptions.TechnicalException;
import com.esgi.pa.domain.services.AuthService;
import com.esgi.pa.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserResource {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CreateUserRequest request) throws TechnicalException {
        return ResponseEntity.ok(
            UserMapper.toDto(
                userService.create(
                    request.name(),
                    request.email(),
                    request.password(),
                    request.role())));
    }

    @PostMapping(value = "login")
    public ResponseEntity<Object> login(@RequestBody UserLoginRequest request) throws FunctionalException, TechnicalException {
        return ResponseEntity.ok(LoginDto.builder()
            .token(authService.createBase64Token(
                userService.login(request.email(), request.password())))
            .build());
    }

    @GetMapping("name/{name}")
    public ResponseEntity<Object> getUserByUsername(@PathVariable String name) throws TechnicalException {
        return ResponseEntity.ok(
            UserMapper.toDto(
                userService.getByName(name)));
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getUserById(@PathVariable UUID id) throws TechnicalException {
        return ResponseEntity.ok(
            UserMapper.toDto(
                userService.getById(id)));
    }

    @GetMapping("{id}/lobbies")
    public ResponseEntity<Object> getLobbies(@PathVariable UUID id) throws TechnicalException {
        return ResponseEntity.ok(
            LobbyMapper.toDto(
                userService.getLobbiesByUserId(id)));
    }
}
