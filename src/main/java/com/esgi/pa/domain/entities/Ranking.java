package com.esgi.pa.domain.entities;

import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Document
@Data
@Builder
public class Ranking {
    
    private UUID id;
    private UUID gameId;
    private UUID playerId;
    private double score;

}
