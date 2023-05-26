package com.esgi.pa.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MESSAGES")
public class Message {
 
    @Id @Default
    private UUID id = UUID.randomUUID();

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    private String content;

    private LocalDateTime sentAt;
}
