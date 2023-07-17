package com.esgi.pa.api.resources;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.esgi.pa.api.dtos.requests.auth.CreateUserRequest;
import com.esgi.pa.api.dtos.requests.auth.UserLoginRequest;
import com.esgi.pa.api.dtos.responses.auth.AuthenticationUserResponse;
import com.esgi.pa.api.dtos.responses.user.GetUserResponse;
import com.esgi.pa.api.mappers.UserMapper;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.AuthService;
import io.swagger.annotations.Api;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Api(tags = "Authentication API")
public class AuthResource {

  private final AuthService authService;

  @PostMapping(value = "login")
  @ResponseStatus(OK)
  public AuthenticationUserResponse login(
    @RequestBody @Valid UserLoginRequest request
  ) throws TechnicalNotFoundException {
    return UserMapper.toAuthenticationUserResponse(
      authService.login(request.email(), request.password())
    );
  }

  @PostMapping(value = "signup")
  @ResponseStatus(CREATED)
  public AuthenticationUserResponse signup(
    @Valid @RequestBody CreateUserRequest request
  ) throws TechnicalFoundException {
    return UserMapper.toAuthenticationUserResponse(
      authService.create(
        request.name(),
        request.email(),
        request.password(),
        request.role()
      )
    );
  }

  @GetMapping
  public String hello() {
    return "Bonjour";
  }
}
