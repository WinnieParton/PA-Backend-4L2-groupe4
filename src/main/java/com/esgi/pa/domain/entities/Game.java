package com.esgi.pa.domain.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "GAMES")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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

    public Game(String name, String description, String gameFiles, String miniature, int minPlayers, int maxPlayers) {
        this.name = name;
        this.description = description;
        this.gameFiles = gameFiles;
        this.miniature = miniature;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
    }

}
