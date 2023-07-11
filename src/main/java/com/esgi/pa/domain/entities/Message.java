package com.esgi.pa.domain.entities;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MESSAGES")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @Column(columnDefinition = "text")
    private String content;

    private LocalDateTime sentAt;

    public Message(Chat chat, User creator, String content, LocalDateTime sentAt) {
        this.chat = chat;
        this.creator = creator;
        this.content = content;
        this.sentAt = sentAt;
    }

}
