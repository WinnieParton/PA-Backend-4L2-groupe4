package com.esgi.pa.server.repositories;

import com.esgi.pa.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.esgi.pa.domain.entities.Lobby;

import java.util.List;

public interface LobbiesRepository extends JpaRepository<Lobby, Long> {
    List<Lobby> findByCreator(User user);
}
