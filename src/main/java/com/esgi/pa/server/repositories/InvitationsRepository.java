package com.esgi.pa.server.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esgi.pa.domain.entities.Invitation;

public interface InvitationsRepository extends JpaRepository<Invitation, UUID> {
    
}
