package com.esgi.pa.api.resources;

import com.esgi.pa.api.dtos.responses.user.GetUserResponse;
import com.esgi.pa.api.mappers.UserMapper;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Api(tags = "User API")
public class UserResource {
    private final UserService userService;

    @GetMapping("{id}/name/{name}")
    public List<GetUserResponse> getUserByUsername(@PathVariable Long id, @PathVariable String name) {
        return UserMapper.toGetUserResponse(
            userService.getByNameAndId(id, name));
    }

    @GetMapping("{id}")
    public GetUserResponse getUserById(@PathVariable Long id) throws TechnicalNotFoundException {
        return UserMapper.toGetUserResponse(
                userService.getById(id));
    }


}
