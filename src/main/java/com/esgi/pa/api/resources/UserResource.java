package com.esgi.pa.api.resources;

import com.esgi.pa.api.dtos.requests.message.SendMessageInPrivate;
import com.esgi.pa.api.dtos.responses.message.ListMessageInPrivateResponse;
import com.esgi.pa.api.dtos.responses.user.GetUserResponse;
import com.esgi.pa.api.mappers.UserMapper;
import com.esgi.pa.domain.entities.MessagePrivate;
import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.ChatService;
import com.esgi.pa.domain.services.MessageService;
import com.esgi.pa.domain.services.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Api(tags = "User API")
public class UserResource {
    private final UserService userService;
    private final ChatService chatService;
    private final MessageService messageService;
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

    @GetMapping("/chat/list/{myUserId}")
    public List<ListMessageInPrivateResponse> getFriends(@PathVariable Long myUserId) throws TechnicalNotFoundException {
        User user = userService.getById(myUserId);
        return chatService.ListchatPrivateResponse(user);
    }
    @GetMapping("/chat/private/{myUserId}/to/{receiveUs}")
    public List<ListMessageInPrivateResponse> processGetPrivateMessage(@PathVariable Long myUserId, @PathVariable Long receiveUs) throws TechnicalNotFoundException {
        User senderUser = userService.getById(myUserId);
        User receiveUser = userService.getById(receiveUs);
        return chatService.chatPrivateResponse(senderUser, receiveUser);
    }

    @PostMapping("/private-chat-message")
    public void processSendPrivateMessage(
            @RequestBody SendMessageInPrivate message
    ) throws TechnicalNotFoundException {
        User senderUser = userService.getById(message.senderUser());
        User receiverUser = userService.getById(message.receiverUser());
        messageService.dispatchMessagePrivate(senderUser, receiverUser, message);
    }
}
