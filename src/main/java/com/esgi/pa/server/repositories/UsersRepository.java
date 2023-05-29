package com.esgi.pa.server.repositories;

import com.esgi.pa.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsersRepository extends JpaRepository<User, UUID> {
    
    Optional<User> findByEmail(String email);

    Optional<User> findByName(String name);
    List<User> findByNameContaining(String name);
}
