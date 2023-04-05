package com.esgi.pa.api.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esgi.pa.api.dtos.CreateUserRequest;
import com.esgi.pa.api.dtos.UserDto;
import com.esgi.pa.api.mappers.UserMapper;
import com.esgi.pa.domain.services.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserResource {
    
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody CreateUserRequest request) {        
        return ResponseEntity.ok(
            UserMapper.toDto(
                userService.create(
                    request.name(),
                    request.email(),
                    request.password(),
                    request.role())));
    }
    

}
