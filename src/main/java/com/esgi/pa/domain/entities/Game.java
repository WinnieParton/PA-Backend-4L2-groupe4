package com.esgi.pa.domain.entities;

import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
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
