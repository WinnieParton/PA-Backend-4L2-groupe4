package com.esgi.pa.domain.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Document
@Data
@Builder
public class Friend {
    
    private User user;
    private boolean accepted;
    
}
