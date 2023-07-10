package com.esgi.pa.api.resources;

import com.esgi.pa.api.dtos.requests.message.SendMessageInLobbyRequest;
import com.esgi.pa.api.dtos.requests.message.SendMessageInPrivate;
import com.esgi.pa.api.dtos.requests.move.GetLobbyRequest;
import com.esgi.pa.api.mappers.LobbyMapper;
import com.esgi.pa.api.mappers.MessageMapper;
import com.esgi.pa.domain.entities.Chat;
import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.entities.Move;
import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatResource {

  private final LobbyService lobbyService;
  private final UserService userService;
  private final MessageService messageService;
  private final ChatService chatService;

  private final MoveService moveService;

  @MessageMapping("/message")
  @SendTo("/chat/lobby")
  public Map<Long, List<SendMessageInLobbyRequest>> processLobbyMessage(
    SendMessageInLobbyRequest message
  ) throws TechnicalNotFoundException {
    Lobby lobby = lobbyService.getById(message.lobby());
    Optional<Chat> chat = chatService.findChatByLobbyWithMessages(lobby);
    return chatService.chatLobbyResponse(
      chat,
      MessageMapper.toGetmessageResponse(chat.get().getMessages())
    );
  }

  @MessageMapping("/private-message")
  public SendMessageInLobbyRequest recMessage(
    @Payload SendMessageInLobbyRequest message
  ) throws TechnicalNotFoundException {
    Lobby lobby = lobbyService.getById(message.lobby());
    User user = userService.getById(message.senderUser());
    messageService.dispatchMessage(
      LobbyMapper.toGetlobbyMessageResponse(lobby),
      user,
      message
    );
    return message;
  }

  /*@MessageMapping("/private/user")
  @SendTo("/chat/private")
  public Map<Long, List<SendMessageInPrivate>> processGetPrivateMessage(SendMessageInPrivate message) throws TechnicalNotFoundException {
    User senderUser = userService.getById(message.senderUser());
    User receiveUser = userService.getById(message.receiverUser());
    System.out.println("receiveUser "+message.toString());
    return chatService.chatPrivateResponse(senderUser, receiveUser);
  }*/
  @MessageMapping("/private/user")
  @SendTo("/chat/private")
  public String processGetPrivateMessage(String message) throws TechnicalNotFoundException {
    
    System.out.println("receiveUser "+message);
    return "Bonjour quoi";
  }

  @MessageMapping("/private-chat-message")
  public SendMessageInPrivate processSendPrivateMessage(
    @Payload SendMessageInPrivate message
  ) throws TechnicalNotFoundException {
    User senderUser = userService.getById(message.senderUser());
    User receiverUser = userService.getById(message.receiverUser());
    messageService.dispatchMessagePrivate(senderUser, receiverUser, message);
    return message;
  }

  @MessageMapping("/game")
  @SendTo("/game/lobby")
  public String processGamePlayer(GetLobbyRequest getLobbyRequest ) throws TechnicalNotFoundException {
    Lobby lobby = lobbyService.getById(getLobbyRequest.lobby());
    Optional<Move> move= moveService.findLastMove(lobby);
    if(move.isPresent())
      return move.get().getGameState();
    else
      return "";
  }
}
