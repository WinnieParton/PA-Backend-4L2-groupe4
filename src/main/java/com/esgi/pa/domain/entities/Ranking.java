package com.esgi.pa.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "RANKINGS")
public class Ranking {
    
    @Id @Default
    private UUID id = UUID.randomUUID();

    @ManyToOne
    private Game game;

    @ManyToOne
    private User player;

    private double score;

}
