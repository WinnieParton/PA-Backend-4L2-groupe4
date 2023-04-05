package com.esgi.pa.domain.entities;

import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;

import com.esgi.pa.domain.enums.RoleEnum;

import lombok.Builder;
import lombok.Value;
import lombok.With;
import lombok.Builder.Default;

@Document
@Value
@Builder
public class User {
    
    @Default
    UUID id = UUID.randomUUID();
    private String name;
    private String email;
    private String password;
    @With
    private RoleEnum role;
    @Default @With
    List<Friend> friends = null;

}
