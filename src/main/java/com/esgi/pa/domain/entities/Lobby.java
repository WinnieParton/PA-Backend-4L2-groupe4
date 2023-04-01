package com.esgi.pa.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Document
@Data
@Builder
public class Lobby {
    
    private UUID id;
    private String name;
    private UUID creator;
    private UUID game;
    private boolean privacySetting;
    private boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

}
