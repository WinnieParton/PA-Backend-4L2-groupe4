package com.esgi.pa.domain.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.esgi.pa.domain.enums.RoleEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @With
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @Default
    @With
    @ManyToMany
    @JoinTable(name = "FRIENDS", joinColumns = @JoinColumn(name = "user1_id"), inverseJoinColumns = @JoinColumn(name = "user2_id"))
    private List<User> friends = List.of();

    @ManyToMany(mappedBy = "players")
    private List<Game> games;

    @ManyToMany(mappedBy = "participants")
    private List<Lobby> participatingLobbies;

    @ManyToMany(mappedBy = "participants")
    private List<Chat> participatingChats;
}
