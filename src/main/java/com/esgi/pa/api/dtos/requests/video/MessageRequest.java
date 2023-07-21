package com.esgi.pa.api.dtos.requests.video;

import lombok.Data;

@Data
public class MessageRequest {
    private String userToCall;
    private String signalData;
    private String from;
    private String name;
}
