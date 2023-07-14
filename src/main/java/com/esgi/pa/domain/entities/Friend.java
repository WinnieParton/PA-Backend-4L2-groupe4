package com.esgi.pa.domain.entities;

import com.esgi.pa.domain.enums.RequestStatus;
import lombok.*;
import lombok.Builder.Default;

import javax.persistence.*;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "FRIENDS")
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User user1;

    @ManyToOne
    private User user2;

    @With
    @Default
    @Enumerated(EnumType.STRING)
    private RequestStatus status = RequestStatus.PENDING;

}
