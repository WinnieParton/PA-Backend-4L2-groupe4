package com.esgi.pa.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.esgi.pa.domain.enums.StatusMessagePrivateEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entité représentant un message privé
 */
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MESSAGESPRIVATE")
public class MessagePrivate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @Column(columnDefinition = "text")
    private String message;

    private String date;

    private StatusMessagePrivateEnum status;

    public MessagePrivate(User sender, User receiver, String message, String date, StatusMessagePrivateEnum status) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.date = date;
        this.status = status;
    }
}
