package com.esgi.pa.domain.entities;

import com.esgi.pa.domain.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.Builder.Default;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Entité représentant un utilisateur
 */
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS")
@JsonIgnoreProperties("participatingLobbies")
public class User implements UserDetails {

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
    @JsonIgnoreProperties({"friends", "games", "participatingLobbies"})
    @JoinTable(name = "FRIENDS", joinColumns = @JoinColumn(name = "user1_id"), inverseJoinColumns = @JoinColumn(name = "user2_id"))
    private List<User> friends = new ArrayList<>();

    @JsonIgnoreProperties({"players", "participants"})
    @ManyToMany(mappedBy = "players")
    private List<Game> games = new ArrayList<>();

    @OneToMany
    private List<Ranking> rankings = new ArrayList<>();

    @ManyToMany(mappedBy = "participants")
    @JsonIgnoreProperties({"players", "participants"})
    private List<Lobby> participatingLobbies = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
