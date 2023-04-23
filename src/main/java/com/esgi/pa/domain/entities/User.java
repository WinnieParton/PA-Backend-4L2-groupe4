package com.esgi.pa.domain.entities;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
    private UUID id = UUID.randomUUID();

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @With @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @With
    @ManyToMany
    @JoinTable(
        name = "friends",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private List<User> friends;

    @ManyToMany(mappedBy = "players")
    private List<Game> games;

    @ManyToMany(mappedBy = "participants")
    private List<Lobby> participatingLobbies;
}
