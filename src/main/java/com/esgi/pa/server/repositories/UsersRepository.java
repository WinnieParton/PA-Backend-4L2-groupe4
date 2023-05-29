package com.esgi.pa.server.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esgi.pa.domain.entities.User;

public interface UsersRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByName(String name);

    List<User> findByNameContaining(String name);
}
