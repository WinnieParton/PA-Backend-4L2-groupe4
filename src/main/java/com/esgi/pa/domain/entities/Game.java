package com.esgi.pa.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "GAMES")
public class Game {
    
    @Id @Default
    private UUID id = UUID.randomUUID();

    private String name;

    private String description;

    private String gameFiles;

    private String miniature;

    private int minPlayers;

    private int maxPlayers;

    @OneToMany(mappedBy = "game")
    private List<Ranking> rankings;

    @ManyToMany
    private List<User> players;
}
