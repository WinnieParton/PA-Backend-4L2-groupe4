package com.esgi.pa.api.resources;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esgi.pa.api.dtos.requests.CreateUserRequest;
import com.esgi.pa.api.dtos.requests.UserLoginRequest;
import com.esgi.pa.api.dtos.responses.CreateUserResponse;
import com.esgi.pa.api.dtos.responses.LoginResponse;
import com.esgi.pa.api.mappers.UserMapper;
import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.exceptions.TechnicalException;
import com.esgi.pa.domain.services.AuthService;
import com.esgi.pa.domain.services.UserService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Api(tags = "Authentication API")
public class AuthResource {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping(value = "login")
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errorMessages);
        }
        try {
            User user = userService.login(request.email(), request.password());
            String token = authService.createBase64Token(user);
            LoginResponse response = LoginResponse.builder().token(token).build();
            return ResponseEntity.ok(response);
        } catch (TechnicalException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMap());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("An error occurred while processing the request.");
        }
    }

    @PostMapping(value = "signup")
    public ResponseEntity<?> signup(@RequestBody CreateUserRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errorMessages);
        }
        try {
        return ResponseEntity.ok(
                UserMapper.toCreateUserResponse(
                        userService.create(
                                request.name(),
                                request.email(),
                                request.password(),
                                request.role())));
        } catch (TechnicalException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMap());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("An error occurred while processing the request.");
        }
    }

}
