package com.esgi.pa.server.adapter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.esgi.pa.domain.entities.User;
import com.esgi.pa.server.PersistenceSpi;
import com.esgi.pa.server.repositories.UsersRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserAdapter implements PersistenceSpi<User, UUID> {
    
    private final UsersRepository usersRepository;
    
    @Override
    public User save(User o) {
        return usersRepository.save(o);
    }

    @Override
    public List<User> saveAll(List<User> oList) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveAll'");
    }

    @Override
    public Optional<User> findById(UUID id) {
        return usersRepository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    @Override
    public Optional<List<User>> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public boolean removeById(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeById'");
    }

    @Override
    public boolean removeAll(List<UUID> ids) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeAll'");
    }
}
