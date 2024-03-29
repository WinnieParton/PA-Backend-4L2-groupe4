package com.esgi.pa.api.resources;

import com.esgi.pa.api.dtos.requests.message.SendMessageInLobbyRequest;
import com.esgi.pa.api.dtos.requests.message.SendMessageInPrivate;
import com.esgi.pa.api.dtos.requests.move.GetLobbyRequest;
import com.esgi.pa.api.dtos.requests.video.CallRequest;
import com.esgi.pa.api.dtos.requests.video.MessageRequest;
import com.esgi.pa.api.dtos.responses.message.ListMessageInPrivateResponse;
import com.esgi.pa.api.dtos.responses.video.MessageResponse;
import com.esgi.pa.api.mappers.LobbyMapper;
import com.esgi.pa.api.mappers.MessageMapper;
import com.esgi.pa.domain.entities.Chat;
import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.entities.Move;
import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.ChatService;
import com.esgi.pa.domain.services.GameService;
import com.esgi.pa.domain.services.LobbyService;
import com.esgi.pa.domain.services.MessageService;
import com.esgi.pa.domain.services.MoveService;
import com.esgi.pa.domain.services.UserService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Contient les routes relatives aux websockets
 */
@RequiredArgsConstructor
@Controller
public class WebsocketResource {

  private final LobbyService lobbyService;
  private final UserService userService;
  private final MessageService messageService;
  private final ChatService chatService;
  private final MoveService moveService;
  private final GameService gameService;

  /**
   * Permet le traitement des messages de lobbys
   *
   * @param message les informations relative au message
   * @return id lobbies et leurs messages
   * @throws TechnicalNotFoundException si un élément n'est pas trouvé
   */
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

  /**
   * Permet le traitment d'un message vers un lobby
   *
   * @param message informations relative au message
   * @return le message nouvellement créé
   * @throws TechnicalNotFoundException si un élément n'est pas trouvé
   */
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

  /**
   * Permet le traitement de l'action d'un joueur sur la partie
   *
   * @param getLobbyRequest les informations à transmettre au moteur de jeu
   * @return le nouvelle état du jeu
   * @throws TechnicalNotFoundException si un élément n'est pas trouvé
   * @throws IOException                si il y a un problème avec le writer
   */
  @MessageMapping("/game")
  @SendTo("/game/lobby")
  public String processGamePlayer(GetLobbyRequest getLobbyRequest)
    throws TechnicalNotFoundException, IOException {
    Lobby lobby = lobbyService.getById(getLobbyRequest.lobby());
    Optional<Move> move = moveService.findLastMoveOutput(lobby);
    if (move.isPresent() && !move.get().getEndPart()) return move
      .get()
      .getGameState();
    return "";
  }

  @MessageMapping("/callUser")
  @SendTo("/chat/callUser")
  public MessageResponse callUser(@Payload MessageRequest request)
    throws TechnicalNotFoundException {
    messageService.dispatchMessageVideo(
      request.getUserToCall(),
      request,
      "call"
    );
    return new MessageResponse(
      request.getSignalData(),
      request.getFrom(),
      request.getName()
    );
  }

  @MessageMapping("/answerCall")
  @SendTo("/chat/callAccepted")
  public MessageRequest answerCall(@Payload MessageRequest request)
    throws TechnicalNotFoundException {
    messageService.dispatchMessageVideo(request.getFrom(), request, "accept");
    return request;
  }

  @MessageMapping("/start/callUser")
  public CallRequest callUserStart(@Payload CallRequest request)
    throws TechnicalNotFoundException {
    messageService.dispatchMessageVideoCall(request, "start");
    return request;
  }

  @MessageMapping("/end/callUser")
  public CallRequest callUserLeave(@Payload CallRequest request)
    throws TechnicalNotFoundException {
    messageService.dispatchMessageVideoCall(request, "leave");
    return request;
  }

  /**
   * Permet le traitment d'un message vers un user
   *
   * @param message informations relative au message
   * @return le message nouvellement créé
   * @throws TechnicalNotFoundException si un élément n'est pas trouvé
   */
  @MessageMapping("/private-message-friend")
  public SendMessageInPrivate sendMessagePrivate(
    @Payload SendMessageInPrivate message
  ) throws TechnicalNotFoundException {
    messageService.dispatchMessagePrivate(
      userService.getById(message.senderUser()),
      userService.getById(message.receiverUser()),
      message
    );
    return message;
  }

  @MessageMapping("/message-friend")
  @SendTo("/chat/message-friend")
  public List<ListMessageInPrivateResponse> processGetPrivateMessage(
    SendMessageInPrivate message
  ) throws TechnicalNotFoundException {
    return chatService.chatPrivateResponse(
      userService.getById(message.senderUser()),
      userService.getById(message.receiverUser())
    );
  }
}
