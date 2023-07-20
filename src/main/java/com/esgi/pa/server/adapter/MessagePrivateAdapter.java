package com.esgi.pa.server.adapter;

import com.esgi.pa.domain.entities.MessagePrivate;
import com.esgi.pa.domain.entities.User;
import com.esgi.pa.server.PersistenceSpi;
import com.esgi.pa.server.repositories.MessagesPrivateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Adapter de persistence pour les messages privés
 */
@Service
@RequiredArgsConstructor
public class MessagePrivateAdapter implements PersistenceSpi<MessagePrivate, Long> {
    private final MessagesPrivateRepository messagesPrivateRepository;

    @Override
    public MessagePrivate save(MessagePrivate o) {
        return messagesPrivateRepository.save(o);
    }

    @Override
    public List<MessagePrivate> saveAll(List<MessagePrivate> oList) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveAll'");
    }

    @Override
    public Optional<MessagePrivate> findById(Long id) {
        return messagesPrivateRepository.findById(id);
    }

    @Override
    public List<MessagePrivate> findAll() {
        return messagesPrivateRepository.findAll();
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

    public List<MessagePrivate> findBySenderOrReceiverOrderByDateDesc(User user1, User user2) {
        return messagesPrivateRepository.findBySenderOrReceiverOrderByDateDesc(user1, user2);
    }

    public List<MessagePrivate> findLastMessagesForUser(User user) {
        return messagesPrivateRepository.findLastMessagesForUser(user);
    }
}
