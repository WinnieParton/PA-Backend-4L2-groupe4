package com.esgi.pa.domain.entities;

import com.esgi.pa.domain.enums.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "INVITATIONS")
public class Invitation {

    @Id
    @Default
    private UUID id = UUID.randomUUID();

    @ManyToOne
    private User user;

    @ManyToOne
    private Lobby lobby;

    @Default
    private RequestStatus accepted = RequestStatus.PENDING;

}
