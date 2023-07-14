package com.esgi.pa.server.adapter;

import com.esgi.pa.domain.entities.MessagePrivate;
import com.esgi.pa.server.PersistenceSpi;
import com.esgi.pa.server.repositories.MessagesPrivateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessagePrivateAdapter implements PersistenceSpi<MessagePrivate, Long> {
    private final MessagesPrivateRepository messagesRepository;

    @Override
    public MessagePrivate save(MessagePrivate o) {
        return messagesRepository.save(o);
    }

    @Override
    public List<MessagePrivate> saveAll(List<MessagePrivate> oList) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveAll'");
    }

    @Override
    public Optional<MessagePrivate> findById(Long id) {
        return messagesRepository.findById(id);
    }

    @Override
    public List<MessagePrivate> findAll() {
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
