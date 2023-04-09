package com.esgi.pa.domain.entities;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Value;

@Value
@Builder
@Entity
@Table(name = "RANKINGS")
public class Ranking {
    
    @Id @Default
    UUID id = UUID.randomUUID();
    @ManyToOne
    private Game game;
    @ManyToOne
    private User player;
    private double score;

}
