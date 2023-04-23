package com.esgi.pa.domain.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.esgi.pa.domain.enums.GameStatusEnum;

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
@Table(name = "LOBBIES")
public class Lobby {
    
    @Id @Default
    private UUID id = UUID.randomUUID();

    private String name;

    @ManyToOne
    private User creator;

    @ManyToOne
    private Game game;

    private boolean isPrivate;

    @With @Enumerated(EnumType.STRING)
    private GameStatusEnum status;

    @Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Default
    private LocalDateTime updateAt = LocalDateTime.now();

    @ManyToMany
    @JoinTable(
        name = "lobby_participants",
        joinColumns = @JoinColumn(name = "lobby_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> participants;

}
