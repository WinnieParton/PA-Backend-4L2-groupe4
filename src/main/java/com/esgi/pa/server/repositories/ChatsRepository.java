package com.esgi.pa.server.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esgi.pa.domain.entities.Chat;
import com.esgi.pa.domain.entities.Lobby;

public interface ChatsRepository extends JpaRepository<Chat, Long> {

    Optional<Chat> findByLobby(Lobby lobby);
    
}
