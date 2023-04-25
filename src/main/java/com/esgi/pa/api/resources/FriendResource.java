package com.esgi.pa.api.resources;

import com.esgi.pa.api.dtos.requests.AddFriendRequest;
import com.esgi.pa.api.dtos.requests.AnswerFriendRequest;
import com.esgi.pa.api.mappers.FriendMapper;
import com.esgi.pa.domain.exceptions.FunctionalException;
import com.esgi.pa.domain.exceptions.TechnicalException;
import com.esgi.pa.domain.services.FriendService;
import com.esgi.pa.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friend")
public class FriendResource {

    private final FriendService friendService;
    private final UserService userService;

    @GetMapping("{senderId}")
    public ResponseEntity<Object> getRequests(@PathVariable UUID senderId) throws FunctionalException {
        return ResponseEntity.ok(
            FriendMapper.toDto(
                friendService.getFriendRequest(
                    userService.getById(senderId))));
    }

    @PutMapping("{senderId}/answer")
    public ResponseEntity<Object> answerRequest(@PathVariable UUID senderId, @RequestBody AnswerFriendRequest request) throws TechnicalException, FunctionalException {
        return ResponseEntity.ok(
            FriendMapper.toDto(
                friendService.handleRequest(
                    userService.getById(senderId),
                    userService.getById(request.receiver()),
                    request.status())));
    }


    @PostMapping("{senderId}")
    public ResponseEntity<Object> add(@PathVariable UUID senderId, @RequestBody AddFriendRequest request) throws FunctionalException {
        return ResponseEntity.ok(
            FriendMapper.toDto(
                friendService.sendRequest(
                    userService.getById(senderId),
                    userService.getById(request.receiver()))));
    }
}
