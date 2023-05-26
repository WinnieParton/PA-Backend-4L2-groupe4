package com.esgi.pa.api.resources;

import com.esgi.pa.api.dtos.requests.AddFriendRequest;
import com.esgi.pa.api.dtos.requests.AnswerFriendRequest;
import com.esgi.pa.api.mappers.FriendMapper;
import com.esgi.pa.api.mappers.FriendRequestsReceivedResponseMapper;
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

    @GetMapping("sent/{senderId}")
    public ResponseEntity<Object> getRequestsSent(@PathVariable UUID senderId) throws TechnicalException {
        return ResponseEntity.ok(
            FriendMapper.toDto(
                friendService.getFriendRequestSent(
                    userService.getById(senderId))));
    }

    @GetMapping("received/{receiverId}")
    public ResponseEntity<Object> getRequestReceived(@PathVariable UUID receiverId) throws TechnicalException {
        return ResponseEntity.ok(
            FriendRequestsReceivedResponseMapper.toDto(
                friendService.getFriendRequestReceived(
                    userService.getById(receiverId))));
    }

    @PutMapping("{receiver}/answer")
    public ResponseEntity<Object> answerRequest(@PathVariable UUID receiver, @RequestBody AnswerFriendRequest request) throws TechnicalException {
        return ResponseEntity.ok(
            FriendMapper.toDto(
                friendService.handleRequest(
                    userService.getById(request.sender()),
                    userService.getById(receiver),
                    request.status())));
    }


    @PostMapping("{receiver}")
    public ResponseEntity<Object> add(@PathVariable UUID receiver, @RequestBody AddFriendRequest request) throws TechnicalException {
        return ResponseEntity.ok(
            FriendMapper.toDto(
                friendService.sendRequest(
                    userService.getById(request.sender()),
                    userService.getById(receiver))));
    }
}
