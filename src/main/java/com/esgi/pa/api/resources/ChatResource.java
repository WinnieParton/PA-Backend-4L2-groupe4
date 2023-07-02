package com.esgi.pa.api.resources;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.esgi.pa.api.dtos.requests.message.SendMessageInPrivate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.esgi.pa.api.dtos.requests.message.SendMessageInLobbyRequest;
import com.esgi.pa.api.mappers.LobbyMapper;
import com.esgi.pa.api.mappers.MessageMapper;
import com.esgi.pa.domain.entities.Chat;
import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.ChatService;
import com.esgi.pa.domain.services.LobbyService;
import com.esgi.pa.domain.services.MessageService;
import com.esgi.pa.domain.services.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ChatResource {
    private final LobbyService lobbyService;
    private final UserService userService;
    private final MessageService messageService;
    private final ChatService chatService;

    @MessageMapping("/message")
    @SendTo("/chat/lobby")
    public Map<Long, List<SendMessageInLobbyRequest>> processLobbyMessage(SendMessageInLobbyRequest message) throws TechnicalNotFoundException {
        Lobby lobby = lobbyService.getById(message.lobby());
        Optional<Chat> chat = chatService.findChatByLobbyWithMessages(lobby);
        return chatService.chatLobbyResponse(chat, MessageMapper.toGetmessageResponse(chat.get().getMessages()));
    }
    @MessageMapping("/private-message")
    public SendMessageInLobbyRequest recMessage(@Payload SendMessageInLobbyRequest message)
            throws TechnicalNotFoundException {
        Lobby lobby = lobbyService.getById(message.lobby());
        User user = userService.getById(message.senderUser());
        messageService.dispatchMessage(LobbyMapper.toGetlobbyMessageResponse(lobby), user, message);
        return message;
    }

    @MessageMapping("/private")
    @SendTo("/chat/private")
    public  Map<Long, List<SendMessageInPrivate>> processGetPrivateMessage(SendMessageInPrivate message) throws TechnicalNotFoundException {
        User senderUser = userService.getById(message.senderUser());
        User receiveUser = userService.getById(message.receiverUser());
        return  chatService.chatPrivateResponse(senderUser, receiveUser);
    }

    @MessageMapping("/private-chat-message")
    public SendMessageInPrivate processSendPrivateMessage(@Payload SendMessageInPrivate message) throws TechnicalNotFoundException {
        User senderUser = userService.getById(message.senderUser());
        User receiverUser = userService.getById(message.receiverUser());
        messageService.dispatchMessagePrivate(senderUser, receiverUser, message);
        return message;
    }
}
