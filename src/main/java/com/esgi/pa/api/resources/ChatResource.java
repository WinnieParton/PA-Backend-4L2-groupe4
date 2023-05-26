package com.esgi.pa.api.resources;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatResource {

    @MessageMapping("/send")
    @SendTo("/chat")
    public Object sendMessage(Object message) {
        return new Object();
    }
}
