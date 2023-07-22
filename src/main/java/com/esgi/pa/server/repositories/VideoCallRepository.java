package com.esgi.pa.server.repositories;

import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.entities.MessagePrivate;
import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.entities.VideoCall;
import com.esgi.pa.domain.enums.VideoStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Interface de persistence pour les appels video
 */
public interface VideoCallRepository extends JpaRepository<VideoCall, Long> {
    Optional<VideoCall> findByLobbyAndVideoStatusEnum(Lobby lobby, VideoStatusEnum videoStatusEnum);
}
