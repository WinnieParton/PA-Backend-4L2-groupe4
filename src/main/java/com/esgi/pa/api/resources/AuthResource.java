package com.esgi.pa.api.resources;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.esgi.pa.api.dtos.requests.CreateUserRequest;
import com.esgi.pa.api.dtos.requests.UserLoginRequest;
import com.esgi.pa.api.dtos.responses.CreateUserResponse;
import com.esgi.pa.api.dtos.responses.LoginResponse;
import com.esgi.pa.api.mappers.UserMapper;
import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.exceptions.MethodArgumentNotValidException;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.AuthService;
import com.esgi.pa.domain.services.ErrorFormatService;
import com.esgi.pa.domain.services.UserService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Api(tags = "Authentication API")
public class AuthResource {

    private final UserService userService;
    private final AuthService authService;
    private final ErrorFormatService errorFormatService;

    @PostMapping(value = "login")
    @ResponseStatus(OK)
    public LoginResponse login(@RequestBody @Valid UserLoginRequest request, BindingResult bindingResult)
            throws TechnicalNotFoundException {
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(
                    errorFormatService.ErrorFormatExceptionHandle(bindingResult.getAllErrors()));
        }
        User user = userService.login(request.email(), request.password());
        String token = authService.createBase64Token(user);
        return LoginResponse.builder().token(token).build();

    }

    @PostMapping(value = "signup")
    @ResponseStatus(CREATED)
    public CreateUserResponse signup(@Valid @RequestBody CreateUserRequest request, BindingResult bindingResult)
            throws TechnicalFoundException {
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(
                    errorFormatService.ErrorFormatExceptionHandle(bindingResult.getAllErrors()));
        }
        return UserMapper.toCreateUserResponse(
                userService.create(
                        request.name(),
                        request.email(),
                        request.password(),
                        request.role()));

    }

}
