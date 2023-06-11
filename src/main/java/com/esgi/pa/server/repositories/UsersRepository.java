package com.esgi.pa.server.repositories;

import com.esgi.pa.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findByNameContainingOrEmailContainingAndIdNot(String name, String name1, Long id);
}
