package com.esgi.pa.domain.entities;

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
@Table(name = "MOVES")
public class Move {
    
    @Id @Default
    private UUID id = UUID.randomUUID();

    @ManyToOne
    private Game game;

    private int turn;
    
    private String gameInstructions;

}
