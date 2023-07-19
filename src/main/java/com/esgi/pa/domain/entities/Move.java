package com.esgi.pa.domain.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Entité représentant un état donnée d'un lors d'une partie
 */
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MOVES")
public class Move {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Lobby lobby;
    @Column(columnDefinition = "text")
    private String gameState;
    @With
    @Column(name = "move_date")
    private LocalDateTime moveDate;
    @With
    private Boolean endPart = Boolean.FALSE;

}
