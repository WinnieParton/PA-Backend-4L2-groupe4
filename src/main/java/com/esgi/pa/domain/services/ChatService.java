package com.esgi.pa.domain.services;

import java.util.Optional;

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
}
