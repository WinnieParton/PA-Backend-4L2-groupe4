package com.esgi.pa.api.resources;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esgi.pa.api.dtos.requests.AddFriendRequest;
import com.esgi.pa.api.dtos.requests.AnswerFriendRequest;
import com.esgi.pa.api.dtos.responses.GetFriendRequestsReceivedResponse;
import com.esgi.pa.api.dtos.responses.GetFriendRequestsSentResponse;
import com.esgi.pa.api.mappers.FriendMapper;
import com.esgi.pa.domain.exceptions.TechnicalException;
import com.esgi.pa.domain.services.FriendService;
import com.esgi.pa.domain.services.UserService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friend")
@Api(tags = "Friend API")
public class FriendResource {

    private final FriendService friendService;
    private final UserService userService;

    @GetMapping("sent/{senderId}")
    public ResponseEntity<?> getRequestsSent(@PathVariable UUID senderId) {
        try {
            return ResponseEntity.ok(
                    new GetFriendRequestsSentResponse(
                            FriendMapper.toGetFriendRequestSentResponse(
                                    friendService.getFriendRequestSent(
                                            userService.getById(senderId)))));
        } catch (TechnicalException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMap());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("An error occurred while processing the request.");
        }
    }

    @GetMapping("received/{receiverId}")
    public ResponseEntity<?> getRequestReceived(@PathVariable UUID receiverId) {
        try {
            return ResponseEntity.ok(
                    new GetFriendRequestsReceivedResponse(
                            FriendMapper.toFriendRequestReceivedResponse(
                                    friendService.getFriendRequestReceived(
                                            userService.getById(receiverId)))));
        } catch (TechnicalException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMap());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("An error occurred while processing the request.");
        }
    }

    @PutMapping("{receiver}/answer")
    public ResponseEntity<?> answerRequest(@PathVariable UUID receiver, @RequestBody AnswerFriendRequest request) {
        try {
            return ResponseEntity.ok(
                    FriendMapper.toAnswerFriendRequestResponse(
                            friendService.handleRequest(
                                    userService.getById(request.sender()),
                                    userService.getById(receiver),
                                    request.status())));
        } catch (TechnicalException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMap());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("An error occurred while processing the request.");
        }
    }

    @PostMapping("{receiver}")
    public ResponseEntity<?> add(@PathVariable UUID receiver, @RequestBody AddFriendRequest request) {
        try {
            return ResponseEntity.ok(
                    FriendMapper.toAddFriendResponse(
                            friendService.sendRequest(
                                    userService.getById(request.sender()),
                                    userService.getById(receiver))));
        } catch (TechnicalException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMap());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("An error occurred while processing the request.");
        }
    }
}
