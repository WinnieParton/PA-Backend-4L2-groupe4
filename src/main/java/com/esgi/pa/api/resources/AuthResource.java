package com.esgi.pa.api.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esgi.pa.api.dtos.requests.UserLoginRequest;
import com.esgi.pa.api.dtos.responses.LoginResponse;
import com.esgi.pa.domain.exceptions.FunctionalException;
import com.esgi.pa.domain.exceptions.TechnicalException;
import com.esgi.pa.domain.services.AuthService;
import com.esgi.pa.domain.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthResource {
    
    private final UserService userService;
    private final AuthService authService;

    @PostMapping(value = "login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserLoginRequest request) throws FunctionalException, TechnicalException {
        return ResponseEntity.ok(
            LoginResponse.builder()
                .token(authService.createBase64Token(
                    userService.login(request.email(), request.password())))
                .build());
    }


}
