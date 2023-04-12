package com.esgi.pa.api.resources;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esgi.pa.api.dtos.FriendDto;
import com.esgi.pa.api.dtos.requests.AddFriendRequest;
import com.esgi.pa.domain.services.FriendService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friend")
public class FriendResource {
    
    private final FriendService friendService;

    @PostMapping("{requesterId}")
    public ResponseEntity<Object> add(@PathVariable UUID requesterId, @RequestBody AddFriendRequest request) {
        return ResponseEntity.ok(
            friendService.add(requesterId, request.requestedId()));
    }

}
