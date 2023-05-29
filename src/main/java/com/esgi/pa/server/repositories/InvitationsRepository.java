package com.esgi.pa.server.repositories;

import com.esgi.pa.domain.entities.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InvitationsRepository extends JpaRepository<Invitation, UUID> {
    
}
