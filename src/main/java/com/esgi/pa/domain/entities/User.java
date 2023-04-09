package com.esgi.pa.domain.entities;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.esgi.pa.domain.enums.RoleEnum;

import lombok.Builder;
import lombok.Value;
import lombok.With;
import lombok.Builder.Default;

@Value
@Builder
@Entity
@Table(name = "USERS")
public class User {
    
    @Id @Default
    UUID id = UUID.randomUUID();
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    @With @Enumerated(EnumType.STRING)
    private RoleEnum role;
    @Default @With
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Friend> friends = null;

}
