package com.esgi.pa.domain.entities;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.esgi.pa.domain.enums.RoleEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import lombok.Builder.Default;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS")
public class User {
    
    @Id @Default
    UUID id = UUID.randomUUID();

    @Column(unique = true)
    String name;

    @Column(unique = true)
    String email;

    String password;

    @With @Enumerated(EnumType.STRING)
    RoleEnum role;

    @Default @With
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Friend> friends = null;

    @ManyToMany(mappedBy = "players")
    private List<Game> games;
}
