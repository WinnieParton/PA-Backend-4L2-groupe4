package com.esgi.pa.api.dtos.requests.video;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class MessageRequest {
    private Long lobby;
    private String userToCall;
    private JsonNode signalData;
    private String from;
    private String name;
}
