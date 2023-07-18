package com.esgi.pa.server.adapter;

import com.esgi.pa.domain.entities.MessagePrivate;
import com.esgi.pa.domain.entities.User;
import com.esgi.pa.server.PersistenceSpi;
import com.esgi.pa.server.repositories.MessagesPrivateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrivateMessageAdapter implements PersistenceSpi<MessagePrivate, Long> {

    private final MessagesPrivateRepository messagesPrivateRepository;

    @Override
    public MessagePrivate save(MessagePrivate o) {
        return null;
    }

    @Override
    public List<MessagePrivate> saveAll(List<MessagePrivate> oList) {
        return null;
    }

    @Override
    public Optional<MessagePrivate> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<MessagePrivate> findAll() {
        return null;
    }

    @Override
    public boolean removeById(Long aLong) {
        return false;
    }

    @Override
    public boolean removeAll(List<Long> longs) {
        return false;
    }

    public List<MessagePrivate> findBySenderOrReceiverOrderByDateDesc(User user1, User user2) {
        return messagesPrivateRepository.findBySenderOrReceiverOrderByDateDesc(user1, user2);
    }

    public List<MessagePrivate> findLastMessagesForUser(User user) {
        return messagesPrivateRepository.findLastMessagesForUser(user);
    }
}
