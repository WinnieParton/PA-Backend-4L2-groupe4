package com.esgi.pa.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entité représentant un jeu
 */
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
    @Column(columnDefinition = "text")
    private String description;

    private String gameFiles;

    private String miniature;

    private int minPlayers;

    private int maxPlayers;

    @OneToMany(mappedBy = "game")
    private List<Ranking> rankings = new ArrayList<>();

    @ManyToMany
    private List<User> players = new ArrayList<>();

}
