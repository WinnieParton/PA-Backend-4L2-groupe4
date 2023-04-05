package com.esgi.pa.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Value;
import lombok.Builder.Default;

@Value
@Builder
@Entity
@Table(name = "LOBBIES")
public class Lobby {
    
    @Id @Default
    UUID id = UUID.randomUUID();
    private String name;
    private User creator;
    private Game game;
    private boolean privacySetting;
    private boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

}
