package com.esgi.pa.api.resources;

import com.esgi.pa.api.dtos.requests.CreateUserRequest;
import com.esgi.pa.api.dtos.requests.UserLoginRequest;
import com.esgi.pa.api.dtos.responses.AuthenticationUserResponse;
import com.esgi.pa.api.mappers.UserMapper;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.AuthService;
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

    private final AuthService authService;

    @PostMapping(value = "login")
    @ResponseStatus(OK)
    public Object login(@RequestBody @Valid UserLoginRequest request) throws TechnicalNotFoundException {
        return authService.login(request.email(), request.password());
    }

    @PostMapping(value = "signup")
    @ResponseStatus(CREATED)
    public AuthenticationUserResponse signup(@Valid @RequestBody CreateUserRequest request) throws TechnicalFoundException {
        return UserMapper.toAuthenticationUserResponse(
                authService.create(
                        request.name(),
                        request.email(),
                        request.password(),
                        request.role()));
    }

}
