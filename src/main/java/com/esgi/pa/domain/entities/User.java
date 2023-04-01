package com.esgi.pa.domain.entities;

import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;

import com.esgi.pa.domain.enums.RoleEnum;

import lombok.Builder;
import lombok.Data;

@Document
@Data
@Builder
public class User {
    
    private UUID id;
    private String name;
    private String email;
    private String password;
    private RoleEnum role;
    private List<Friend> friends;

}
