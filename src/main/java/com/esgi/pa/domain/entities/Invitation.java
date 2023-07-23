package com.esgi.pa.domain.entities;

import com.esgi.pa.domain.enums.RequestStatus;
import lombok.*;
import lombok.Builder.Default;

import javax.persistence.*;

/**
 * Entité représentant une invitation à un lobby
 */
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "INVITATIONS")
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Lobby lobby;

    @Default
    @With
    private RequestStatus accepted = RequestStatus.PENDING;

}
