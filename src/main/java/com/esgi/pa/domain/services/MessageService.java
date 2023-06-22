package com.esgi.pa.domain.services;

import com.esgi.pa.api.dtos.responses.lobby.GetlobbyMessageResponse;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.esgi.pa.api.dtos.requests.message.SendMessageInLobbyRequest;
import com.esgi.pa.domain.entities.Chat;
import com.esgi.pa.domain.entities.Message;
import com.esgi.pa.domain.entities.User;
import com.esgi.pa.server.adapter.MessageAdapter;

import lombok.RequiredArgsConstructor;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MessageService {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    private final MessageAdapter messageAdapter;
    private final ChatService chatService;
    private final LobbyService lobbyService;
    public void dispatchMessage(GetlobbyMessageResponse lobby, User user, SendMessageInLobbyRequest message) throws TechnicalNotFoundException {
        lobby.participants().forEach(participant -> {
            if (!Objects.equals(participant.id(), user.getId())) {
                simpMessagingTemplate.convertAndSendToUser(participant.name(), "/private", message);
            }
        });
        Chat chat = chatService.saveChat(lobbyService.getById(lobby.id()));
        Message message2 = new Message(chat, user, message.message());
        messageAdapter.save(message2);
    }
}
