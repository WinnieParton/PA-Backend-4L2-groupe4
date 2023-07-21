package com.esgi.pa.api.dtos.responses.video;

import lombok.Data;

@Data
public class MessageResponse {
    private String signalData;
    private String from;
    private String name;

    public MessageResponse(String signalData, String from, String name) {
        this.signalData = signalData;
        this.from = from;
        this.name = name;
    }
}
