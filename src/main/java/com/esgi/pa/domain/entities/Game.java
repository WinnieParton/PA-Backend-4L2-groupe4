package com.esgi.pa.domain.entities;

import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.esgi.pa.domain.enums.GameStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;
import lombok.With;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "GAMES")
public class Game {
    
    @Id @Default
    UUID id = UUID.randomUUID();

    String name;

    String description;

    String gameFiles;

    String miniature;

    int minPlayers;

    int maxPlayers;

    @With @Enumerated(EnumType.STRING)
    GameStatusEnum status;

    @OneToMany(mappedBy = "game")
    List<Ranking> rankings;

    @ManyToMany
    private List<User> players;
}
