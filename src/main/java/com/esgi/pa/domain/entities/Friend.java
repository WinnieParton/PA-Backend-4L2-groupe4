package com.esgi.pa.domain.entities;

import com.esgi.pa.domain.enums.RequestStatus;
import lombok.*;
import lombok.Builder.Default;

import javax.persistence.*;
import java.util.UUID;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "FRIENDS")
public class Friend {

    @Id
    @Default
    private UUID id = UUID.randomUUID();

    @ManyToOne
    private User user;

    @ManyToOne
    private User friend;

    @With
    @Default
    @Enumerated(EnumType.STRING)
    private RequestStatus status = RequestStatus.PENDING;

}
