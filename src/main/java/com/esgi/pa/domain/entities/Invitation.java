package com.esgi.pa.domain.entities;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Value;

@Value
@Builder
@Entity
@Table(name = "INVITATIONS")
public class Invitation {
    
    @Id @Default
    UUID id = UUID.randomUUID();
    @ManyToOne
    private User user;
    @ManyToOne
    private Lobby lobby;
    private boolean accepted;

}
