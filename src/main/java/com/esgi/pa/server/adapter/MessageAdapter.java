package com.esgi.pa.server.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.esgi.pa.domain.entities.Message;
import com.esgi.pa.server.PersistenceSpi;
import com.esgi.pa.server.repositories.MessagesRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageAdapter implements PersistenceSpi<Message, Long> {
    private final MessagesRepository messagesRepository;

    @Override
    public Message save(Message o) {
        return messagesRepository.save(o);
    }

    @Override
    public List<Message> saveAll(List<Message> oList) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveAll'");
    }

    @Override
    public Optional<Message> findById(Long id) {
        return messagesRepository.findById(id);
    }

    @Override
    public List<Message> findAll() {
        return messagesRepository.findAll();
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

}
