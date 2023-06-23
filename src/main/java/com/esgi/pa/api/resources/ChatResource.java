package com.esgi.pa.api.resources;

import com.esgi.pa.api.mappers.LobbyMapper;
import com.esgi.pa.api.mappers.MessageMapper;
import com.esgi.pa.domain.entities.Chat;
import org.hibernate.Hibernate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.esgi.pa.api.dtos.requests.message.SendMessageInLobbyRequest;
import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.ChatService;
import com.esgi.pa.domain.services.LobbyService;
import com.esgi.pa.domain.services.MessageService;
import com.esgi.pa.domain.services.UserService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class ChatResource {
    private final LobbyService lobbyService;
    private final UserService userService;
    private final MessageService messageService;
    private final ChatService chatService;

    @MessageMapping("/privateChatMessage")
    @SendTo("/chat/private")
    public Object processPrivateMessage(Object message) {
        return new Object();
    }

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
}
