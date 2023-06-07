package com.esgi.pa.domain.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.esgi.pa.domain.enums.GameStatusEnum;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.Builder.Default;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LOBBIES")
public class Lobby {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private User creator;

    @ManyToOne(fetch = FetchType.LAZY)
    private Game game;

    private boolean isPrivate;

    @With
    @Enumerated(EnumType.STRING)
    private GameStatusEnum status;

    @Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Default
    private LocalDateTime updateAt = LocalDateTime.now();

    @JsonIgnoreProperties({"friends", "games", "participatingLobbies", "participatingChats"})
    @ManyToMany
    @JoinTable(name = "lobby_participants", joinColumns = @JoinColumn(name = "lobby_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> participants= new ArrayList<>();

}
