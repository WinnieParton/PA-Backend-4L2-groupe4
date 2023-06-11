package com.esgi.pa.domain.entities;

import com.esgi.pa.domain.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.Builder.Default;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS")
@JsonIgnoreProperties("participatingLobbies")
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
    @JsonIgnoreProperties({"friends", "games", "participatingLobbies", "participatingChats"})
    @Default
    @With
    @ManyToMany
    @JoinTable(name = "FRIENDS", joinColumns = @JoinColumn(name = "user1_id"), inverseJoinColumns = @JoinColumn(name = "user2_id"))
    private List<User> friends = new ArrayList<>();

    @JsonIgnoreProperties({"players", "participants"})
    @ManyToMany(mappedBy = "players")
    private List<Game> games = new ArrayList<>();

    @JsonIgnoreProperties({"players", "participants"})
    @ManyToMany(mappedBy = "participants")
    private List<Lobby> participatingLobbies = new ArrayList<>();

    @JsonIgnoreProperties("participants")
    @ManyToMany(mappedBy = "participants")
    private List<Chat> participatingChats = new ArrayList<>();
}
