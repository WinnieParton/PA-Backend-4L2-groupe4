package com.esgi.pa.domain.entities;

import lombok.*;

import javax.persistence.*;

/**
 * Entité représentant un rang pour un utilisateur sur un jeu
 */
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "RANKINGS")
public class Ranking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Game game;

    @ManyToOne
    private User player;

    @With
    private Double score;

    @With
    private Double gamePlayed;

    public Ranking(Game game, User player) {
        this.game = game;
        this.player = player;
    }
}
