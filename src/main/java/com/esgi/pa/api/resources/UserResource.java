package com.esgi.pa.api.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esgi.pa.api.dtos.UserDto;
import com.esgi.pa.api.dtos.requests.CreateUserRequest;
import com.esgi.pa.api.dtos.requests.GetByUsernameRequest;
import com.esgi.pa.api.dtos.requests.UserLoginRequest;
import com.esgi.pa.api.mappers.UserMapper;
import com.esgi.pa.domain.exceptions.FunctionalException;
import com.esgi.pa.domain.exceptions.TechnicalException;
import com.esgi.pa.domain.services.AuthService;
import com.esgi.pa.domain.services.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserResource {
    
    private final UserService userService;
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CreateUserRequest request) throws FunctionalException {        
        return ResponseEntity.ok(
            UserMapper.toDto(
                userService.create(
                    request.name(),
                    request.email(),
                    request.password(),
                    request.role())));
    }
    
    @PostMapping(value="login")
    public ResponseEntity<Object> login(@RequestBody UserLoginRequest request) throws FunctionalException, TechnicalException {
        return ResponseEntity.ok(
            authService.createBase64Token(
                userService.login(request.email(), request.password())));
    }
    
    @GetMapping
    public ResponseEntity<UserDto> getUserByUsername(@RequestBody GetByUsernameRequest request) throws FunctionalException {
        return ResponseEntity.ok(
            UserMapper.toDto(
                userService.getByName(request.name())));
    }
    
}
