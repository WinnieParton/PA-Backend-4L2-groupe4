package com.esgi.pa.domain.entities;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.esgi.pa.domain.enums.GameStatusEnum;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Value;

@Value
@Builder
@Entity
@Table(name = "GAMES")
public class Game {
    
    @Id @Default
    UUID id = UUID.randomUUID();
    private String name;
    private String description;
    private String gameFiles;
    private String miniature;
    private int minPlayers;
    private int maxPlayers;
    private GameStatusEnum status;
    
}
