package com.esgi.pa.domain.entities;

import com.esgi.pa.domain.enums.RoleEnum;
import lombok.*;
import lombok.Builder.Default;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

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

    @Default @With
    @ManyToMany
    @JoinTable(
        name = "FRIENDS",
        joinColumns = @JoinColumn(name = "user1_id"),
        inverseJoinColumns = @JoinColumn(name = "user2_id"))
    private List<User> friends = List.of();

    @ManyToMany(mappedBy = "players")
    private List<Game> games;

    @ManyToMany(mappedBy = "participants")
    private List<Lobby> participatingLobbies;
}
