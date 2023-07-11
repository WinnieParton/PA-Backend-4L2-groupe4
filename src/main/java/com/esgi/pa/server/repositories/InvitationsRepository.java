package com.esgi.pa.server.repositories;

import com.esgi.pa.domain.entities.Invitation;
import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InvitationsRepository extends JpaRepository<Invitation, Long> {

    Optional<Invitation> getInvitationByUserAndLobby(User user, Lobby lobby);

    List<Invitation> findAllByUser(User user);
}
