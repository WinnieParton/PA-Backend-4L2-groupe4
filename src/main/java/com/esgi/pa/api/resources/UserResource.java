package com.esgi.pa.api.resources;

import com.esgi.pa.api.dtos.requests.CreateUserRequest;
import com.esgi.pa.api.dtos.responses.CreateUserResponse;
import com.esgi.pa.api.dtos.responses.GetUserResponse;
import com.esgi.pa.api.dtos.responses.GetlobbiesResponse;
import com.esgi.pa.api.mappers.LobbyMapper;
import com.esgi.pa.api.mappers.UserMapper;
import com.esgi.pa.domain.exceptions.TechnicalException;
import com.esgi.pa.domain.services.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Api(tags = "User API")
public class UserResource {
    private final UserService userService;

    @GetMapping("name/{name}")
    public ResponseEntity<GetUserResponse> getUserByUsername(@PathVariable String name) throws TechnicalException {
        return ResponseEntity.ok(
            UserMapper.toGetUserResponse(
                userService.getByName(name)));
    }

    @GetMapping("{id}")
    public ResponseEntity<GetUserResponse> getUserById(@PathVariable UUID id) throws TechnicalException {
        return ResponseEntity.ok(
            UserMapper.toGetUserResponse(
                userService.getById(id)));
    }

    @GetMapping("{id}/lobbies")
    public ResponseEntity<GetlobbiesResponse> getLobbies(@PathVariable UUID id) throws TechnicalException {
        return ResponseEntity.ok(
            new GetlobbiesResponse(
                LobbyMapper.toGetlobbyResponse(
                    userService.getLobbiesByUserId(id))));
    }
}
