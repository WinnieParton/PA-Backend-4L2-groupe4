package com.esgi.pa.server.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.esgi.pa.domain.entities.User;
import com.esgi.pa.server.PersistenceSpi;
import com.esgi.pa.server.repositories.UsersRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserAdapter implements PersistenceSpi<User, Long> {

    private final UsersRepository usersRepository;

    @Override
    public User save(User user) {
        return usersRepository.save(user);
    }

    @Override
    public List<User> saveAll(List<User> oList) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveAll'");
    }

    @Override
    public Optional<User> findById(Long id) {
        return usersRepository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    public List<User> findByName(String name) {
        return usersRepository.findByNameContaining(name);
    }

    @Override
    public List<User> findAll() {
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
