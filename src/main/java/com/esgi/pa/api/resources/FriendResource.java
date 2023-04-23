package com.esgi.pa.api.resources;

import java.util.UUID;

import javax.websocket.server.PathParam;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esgi.pa.api.dtos.FriendDto;
import com.esgi.pa.api.dtos.requests.AddFriendRequest;
import com.esgi.pa.domain.exceptions.FunctionalException;
import com.esgi.pa.domain.exceptions.TechnicalException;
import com.esgi.pa.domain.services.FriendService;
import com.esgi.pa.domain.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friend")
public class FriendResource {
    
    private final FriendService friendService;
    private final UserService userService;

    @PostMapping("{senderId}")
    public ResponseEntity<Object> add(@PathVariable UUID senderId, @RequestBody AddFriendRequest request) throws TechnicalException, FunctionalException {
        return ResponseEntity.ok(
            friendService.add(
                userService.getById(senderId), 
                userService.getById(request.recieverId())));
    }
}
