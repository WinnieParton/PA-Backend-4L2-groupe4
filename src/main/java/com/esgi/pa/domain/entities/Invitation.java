package com.esgi.pa.domain.entities;

import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Document
@Data
@Builder
public class Invitation {
    
    private UUID id;
    private User user;
    private Lobby lobby;
    private boolean accepted;

}
