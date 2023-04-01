package com.esgi.pa.domain.entities;

import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;

import com.esgi.pa.domain.enums.GameStatusEnum;

import lombok.Builder;
import lombok.Data;

@Document
@Data
@Builder
public class Game {
    
    private UUID id;
    private String name;
    private int minPlayers;
    private int maxPlayers;
    private GameStatusEnum status;
    
}
