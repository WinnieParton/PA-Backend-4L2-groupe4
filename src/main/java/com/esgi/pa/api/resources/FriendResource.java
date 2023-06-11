package com.esgi.pa.api.resources;

import com.esgi.pa.api.dtos.requests.AddFriendRequest;
import com.esgi.pa.api.dtos.requests.AnswerFriendRequest;
import com.esgi.pa.api.dtos.responses.AddFriendResponse;
import com.esgi.pa.api.dtos.responses.AnswerFriendRequestResponse;
import com.esgi.pa.api.dtos.responses.GetFriendRequestsReceivedResponse;
import com.esgi.pa.api.dtos.responses.GetFriendRequestsSentResponse;
import com.esgi.pa.api.mappers.FriendMapper;
import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.exceptions.MethodArgumentNotValidException;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.ErrorFormatService;
import com.esgi.pa.domain.services.FriendService;
import com.esgi.pa.domain.services.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friend")
@Api(tags = "Friend API")
public class FriendResource {

    private final FriendService friendService;
    private final UserService userService;
    private final ErrorFormatService errorFormatService;

    @GetMapping("sent/{senderId}")
    public GetFriendRequestsSentResponse getRequestsSent(@PathVariable Long senderId) throws TechnicalNotFoundException {
        return new GetFriendRequestsSentResponse(
                FriendMapper.toGetFriendRequestSentResponse(
                        friendService.getFriendRequestSent(
                                userService.getById(senderId))));
    }

    @GetMapping("received/{receiverId}")
    public GetFriendRequestsReceivedResponse getRequestReceived(@PathVariable Long receiverId) throws TechnicalNotFoundException {

        return new GetFriendRequestsReceivedResponse(
                FriendMapper.toFriendRequestReceivedResponse(
                        friendService.getFriendRequestReceived(
                                userService.getById(receiverId))));
    }

    @GetMapping("{myUserId}")
    public GetFriendRequestsReceivedResponse getFriends(@PathVariable Long myUserId) throws TechnicalNotFoundException {
        User user = userService.getById(myUserId);
        return new GetFriendRequestsReceivedResponse(
                FriendMapper.toFriendRequestResponse(friendService.getFriends(user), user));
    }

    @PutMapping("{receiver}/answer")
    @ResponseStatus(OK)
    public AnswerFriendRequestResponse answerRequest(@PathVariable Long receiver,
                                                     @Valid @RequestBody AnswerFriendRequest request, BindingResult bindingResult) throws TechnicalNotFoundException {
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(
                    errorFormatService.ErrorFormatExceptionHandle(bindingResult.getAllErrors()));
        }
        return FriendMapper.toAnswerFriendRequestResponse(
                friendService.handleRequest(
                        userService.getById(request.sender()),
                        userService.getById(receiver),
                        request.status()));
    }

    @PostMapping("{receiver}")
    @ResponseStatus(CREATED)
    public AddFriendResponse add(@PathVariable Long receiver, @Valid @RequestBody AddFriendRequest request, BindingResult bindingResult) throws TechnicalFoundException, TechnicalNotFoundException {
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(
                    errorFormatService.ErrorFormatExceptionHandle(bindingResult.getAllErrors()));
        }
        return FriendMapper.toAddFriendResponse(
                friendService.sendRequest(
                        userService.getById(request.sender()),
                        userService.getById(receiver)));
    }
}
