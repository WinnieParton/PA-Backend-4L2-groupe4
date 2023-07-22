package com.esgi.pa.api.dtos.requests.video;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class CallRequest {
    private Boolean call;
    private Long lobby;
    private Long userConnect;
}
