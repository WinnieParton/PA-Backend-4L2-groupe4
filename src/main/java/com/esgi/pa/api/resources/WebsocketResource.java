package com.esgi.pa.api.resources;

import com.esgi.pa.api.dtos.requests.message.SendMessageInLobbyRequest;
import com.esgi.pa.api.dtos.requests.move.GetLobbyRequest;
import com.esgi.pa.api.mappers.LobbyMapper;
import com.esgi.pa.api.mappers.MessageMapper;
import com.esgi.pa.domain.entities.Chat;
import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.entities.Move;
import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public Map<Long, List<SendMessageInLobbyRequest>> processLobbyMessage(SendMessageInLobbyRequest message) throws TechnicalNotFoundException {
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
    public SendMessageInLobbyRequest recMessage(@Payload SendMessageInLobbyRequest message) throws TechnicalNotFoundException {
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
    public String processGamePlayer(GetLobbyRequest getLobbyRequest) throws TechnicalNotFoundException, IOException {
        Lobby lobby = lobbyService.getById(getLobbyRequest.lobby());
        Optional<Move> move = moveService.findLastMoveOutput(lobby);
        if (move.isPresent() && !move.get().getEndPart())
            return move.get().getGameState();
        return "";
    }
}
