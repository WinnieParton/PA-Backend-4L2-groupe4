package com.esgi.pa.domain.entities;

import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.esgi.pa.domain.enums.GameStatusEnum;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Value;
import lombok.With;

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
    @With @Enumerated(EnumType.STRING)
    private GameStatusEnum status;
    @OneToMany(mappedBy = "game")
    private List<Ranking> rankings;

}
