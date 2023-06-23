package com.esgi.pa.domain.services;

import java.util.*;

import com.esgi.pa.api.dtos.requests.message.SendMessageInLobbyRequest;
import com.esgi.pa.api.dtos.responses.message.ReceiveMessageInLobbyResponse;
import com.esgi.pa.domain.enums.StatusMessageEnum;
import org.springframework.stereotype.Service;

import com.esgi.pa.domain.entities.Chat;
import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.server.adapter.ChatAdapter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatAdapter chatAdapter;

    public Chat saveChat(Lobby lobby) {
        Optional<Chat> chat =  findChatByLobby(lobby);
        if(chat.isPresent()) return chat.get();
        else return chatAdapter.save(new Chat(lobby));
    }

    public Optional<Chat> findChatByLobby(Lobby lobby) {
        return chatAdapter.findChatByLobby(lobby);
    }
    public Optional<Chat> findChatByLobbyWithMessages(Lobby lobby) {
        return chatAdapter.findChatByLobbyWithMessages(lobby);
    }

    public Map<Long, List<SendMessageInLobbyRequest>> chatLobbyResponse(Optional<Chat> chat, List<ReceiveMessageInLobbyResponse> receiveMessageInLobbyResponses){
        Map<Long, List<SendMessageInLobbyRequest>> privateChats = new HashMap<>();
        List<SendMessageInLobbyRequest> messages = new ArrayList<>();

        receiveMessageInLobbyResponses.forEach(message ->{
            messages.add(new SendMessageInLobbyRequest(
                    message.senderName(),
                    chat.get().getLobby().getId(),
                    message.message(),
                    chat.get().getLobby().getParticipants().stream() .filter(
                            user -> user.getId() == message.senderName())
                            .findFirst().get().getName(),
                    chat.get().getLobby().getParticipants().stream() .filter(
                                    user -> user.getId() != message.senderName())
                            .findFirst().get().getName(),
                    StatusMessageEnum.JOIN,
                    message.currentDate()
                    ));
        });
        privateChats.put(chat.get().getLobby().getId(), messages);
        return privateChats;
    }
}
