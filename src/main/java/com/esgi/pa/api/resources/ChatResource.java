package com.esgi.pa.api.resources;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatResource {

    @MessageMapping("/privateChatMessage")
    @SendTo("/chat/private")
    public Object processPrivateMessage(Object message) {
        return new Object();
    }

    @MessageMapping("/lobbyChatMessage")
    @SendTo("/chat/lobby")
    public Object processLobbyMessage(Object message) {
        return new Object();
    }
}
