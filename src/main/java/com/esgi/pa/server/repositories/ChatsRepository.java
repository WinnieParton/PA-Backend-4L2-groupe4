package com.esgi.pa.server.repositories;

import com.esgi.pa.domain.entities.Chat;
import com.esgi.pa.domain.entities.Lobby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ChatsRepository extends JpaRepository<Chat, Long> {

    Optional<Chat> findByLobby(Lobby lobby);
    @Query("SELECT c FROM Chat c LEFT JOIN FETCH c.messages WHERE c.lobby = :lobby")
    Optional<Chat> findByLobbyWithMessages(@Param("lobby") Lobby lobby);
}
