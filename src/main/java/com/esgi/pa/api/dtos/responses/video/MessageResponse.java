package com.esgi.pa.api.dtos.responses.video;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class MessageResponse {
    private JsonNode  signalData;
    private String from;
    private String name;

    public MessageResponse(JsonNode signalData, String from, String name) {
        this.signalData = signalData;
        this.from = from;
        this.name = name;
    }
}
