package com.esgi.pa.server.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.esgi.pa.domain.entities.Chat;
import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.server.PersistenceSpi;
import com.esgi.pa.server.repositories.ChatsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatAdapter implements PersistenceSpi<Chat, Long> {
    private final ChatsRepository chatsRepository;

    @Override
    public Chat save(Chat o) {
        return chatsRepository.save(o);
    }

    @Override
    public List<Chat> saveAll(List<Chat> oList) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveAll'");
    }

    @Override
    public Optional<Chat> findById(Long id) {
        return chatsRepository.findById(id);
    }

    @Override
    public List<Chat> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public boolean removeById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeById'");
    }

    @Override
    public boolean removeAll(List<Long> ids) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeAll'");
    }

    public Optional<Chat> findChatByLobby(Lobby lobby) {
        return chatsRepository.findByLobby(lobby);
    }
    public Optional<Chat> findChatByLobbyWithMessages(Lobby lobby) {
        return chatsRepository.findByLobbyWithMessages(lobby);
    }
}
