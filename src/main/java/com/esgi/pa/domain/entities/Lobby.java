package com.esgi.pa.domain.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.esgi.pa.domain.enums.GameStatusEnum;

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

    @ManyToMany
    @JoinTable(name = "lobby_participants", joinColumns = @JoinColumn(name = "lobby_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> participants= new ArrayList<>();

}
