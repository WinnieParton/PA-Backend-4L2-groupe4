package com.esgi.pa.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LOBBIES")
public class Lobby {
    
    @Id @Default
    UUID id = UUID.randomUUID();

    String name;

    @ManyToOne
    User creator;

    @ManyToOne
    Game game;

    boolean privacySetting;

    boolean status;

    LocalDateTime createdAt;

    LocalDateTime updateAt;

}
