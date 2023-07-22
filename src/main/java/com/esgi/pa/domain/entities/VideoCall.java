package com.esgi.pa.domain.entities;

import com.esgi.pa.domain.enums.VideoStatusEnum;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "VIDEOCALL")
public class VideoCall {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userToCall;
    @Column(columnDefinition = "text")
    private String signalData;
    private String callFrom;
    private String name;
    @ManyToOne
    @JoinColumn(name = "lobby_id")
    private Lobby lobby;
    private VideoStatusEnum videoStatusEnum;

    public void setSignalData(JsonNode signalData) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            this.signalData = objectMapper.writeValueAsString(signalData);
        } catch (Exception e) {
            // Handle the exception or log the error
            e.printStackTrace();
        }
    }

    public JsonNode getSignalData() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(signalData);
        } catch (Exception e) {
            // Handle the exception or log the error
            e.printStackTrace();
        }
        return null;
    }
}
