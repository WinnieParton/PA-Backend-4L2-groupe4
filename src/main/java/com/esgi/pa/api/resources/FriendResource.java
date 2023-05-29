package com.esgi.pa.api.resources;

import com.esgi.pa.api.dtos.requests.AddFriendRequest;
import com.esgi.pa.api.dtos.requests.AnswerFriendRequest;
import com.esgi.pa.api.dtos.responses.AddFriendResponse;
import com.esgi.pa.api.dtos.responses.AnswerFriendRequestResponse;
import com.esgi.pa.api.dtos.responses.GetFriendRequestsReceivedResponse;
import com.esgi.pa.api.dtos.responses.GetFriendRequestsSentResponse;
import com.esgi.pa.api.mappers.FriendMapper;
import com.esgi.pa.domain.exceptions.TechnicalException;
import com.esgi.pa.domain.services.FriendService;
import com.esgi.pa.domain.services.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friend")
@Api(tags = "Friend API")
public class FriendResource {

    private final FriendService friendService;
    private final UserService userService;

    @GetMapping("sent/{senderId}")
    public ResponseEntity<GetFriendRequestsSentResponse> getRequestsSent(@PathVariable UUID senderId) throws TechnicalException {
        return ResponseEntity.ok(
            new GetFriendRequestsSentResponse(
                FriendMapper.toGetFriendRequestSentResponse(
                    friendService.getFriendRequestSent(
                        userService.getById(senderId)))));
    }

    @GetMapping("received/{receiverId}")
    public ResponseEntity<GetFriendRequestsReceivedResponse> getRequestReceived(@PathVariable UUID receiverId) throws TechnicalException {
        return ResponseEntity.ok(
            new GetFriendRequestsReceivedResponse(
                FriendMapper.toFriendRequestReceivedResponse(
                    friendService.getFriendRequestReceived(
                        userService.getById(receiverId)))));
    }

    @PutMapping("{receiver}/answer")
    public ResponseEntity<AnswerFriendRequestResponse> answerRequest(@PathVariable UUID receiver, @RequestBody AnswerFriendRequest request) throws TechnicalException {
        return ResponseEntity.ok(
            FriendMapper.toAnswerFriendRequestResponse(
                friendService.handleRequest(
                    userService.getById(request.sender()),
                    userService.getById(receiver),
                    request.status())));
    }


    @PostMapping("{receiver}")
    public ResponseEntity<AddFriendResponse> add(@PathVariable UUID receiver, @RequestBody AddFriendRequest request) throws TechnicalException {
        return ResponseEntity.ok(
            FriendMapper.toAddFriendResponse(
                friendService.sendRequest(
                    userService.getById(request.sender()),
                    userService.getById(receiver))));
    }
}
