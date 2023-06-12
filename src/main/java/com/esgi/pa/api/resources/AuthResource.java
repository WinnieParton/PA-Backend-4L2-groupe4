package com.esgi.pa.api.resources;

import com.esgi.pa.api.dtos.requests.CreateUserRequest;
import com.esgi.pa.api.dtos.requests.UserLoginRequest;
import com.esgi.pa.api.dtos.responses.CreateUserResponse;
import com.esgi.pa.api.dtos.responses.LoginResponse;
import com.esgi.pa.api.mappers.UserMapper;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.AuthService;
import com.esgi.pa.domain.services.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Api(tags = "Authentication API")
public class AuthResource {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping(value = "login")
    @ResponseStatus(OK)
    public LoginResponse login(@RequestBody @Valid UserLoginRequest request) throws TechnicalNotFoundException {
        return LoginResponse.builder()
                .token(authService.createBase64Token(
                        userService.login(request.email(), request.password())))
                .build();
    }

    @PostMapping(value = "signup")
    @ResponseStatus(CREATED)
    public CreateUserResponse signup(@Valid @RequestBody CreateUserRequest request) throws TechnicalFoundException {
        return UserMapper.toCreateUserResponse(
                userService.create(
                        request.name(),
                        request.email(),
                        request.password(),
                        request.role()));
    }

}
