package com.esgi.pa.server.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.esgi.pa.domain.entities.Friend;
import com.esgi.pa.domain.entities.User;
import com.esgi.pa.server.PersistenceSpi;
import com.esgi.pa.server.repositories.FriendsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FriendAdapter implements PersistenceSpi<Friend, Long> {

    private final FriendsRepository repository;

    @Override
    public Friend save(Friend o) {
        return repository.save(o);
    }

    @Override
    public List<Friend> saveAll(List<Friend> oList) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveAll'");
    }

    @Override
    public Optional<Friend> findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    public Optional<Friend> findByUserAndFriend(User user, User friend) {
        return repository.findByUser1AndUser2(user, friend);
    }

    public List<Friend> findByUser1(User o) {
        return repository.findByUser1(o);
    }

    public List<Friend> findByUser2(User o) {
        return repository.findByUser2(o);
    }

    @Override
    public List<Friend> findAll() {
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

}
