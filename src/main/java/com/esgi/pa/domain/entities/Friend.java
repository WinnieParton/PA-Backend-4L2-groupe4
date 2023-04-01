package com.esgi.pa.domain.entities;

import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Document
@Data
@Builder
public class Friend {
    
    private UUID playerId;
    private UUID friendId;
    private boolean accepted;
    
}
