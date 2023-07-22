package com.esgi.pa.domain.entities;

import com.esgi.pa.domain.enums.GameStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.Builder.Default;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entité représentant un salon de jeu
 */
@Data
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

    @OneToMany
    private List<Move> moves = new ArrayList<>();

    private boolean invitationOnly;

    @Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Default
    private LocalDateTime updateAt = LocalDateTime.now();

    @With
    @JsonIgnoreProperties({ "friends", "games", "participatingLobbies" })
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "lobby_participants", joinColumns = @JoinColumn(name = "lobby_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> participants = new ArrayList<>();

    @OneToOne(mappedBy = "lobby", cascade = CascadeType.ALL)
    private Chat chat;
    @OneToMany(mappedBy = "lobby", fetch = FetchType.LAZY)
    private List<VideoCall> videoCalls = new ArrayList<>();
}
