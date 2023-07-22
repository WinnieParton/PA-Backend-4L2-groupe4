package com.esgi.pa.domain.services;

import com.esgi.pa.api.dtos.requests.message.SendMessageInLobbyRequest;
import com.esgi.pa.api.dtos.requests.message.SendMessageInPrivate;
import com.esgi.pa.api.dtos.requests.video.CallRequest;
import com.esgi.pa.api.dtos.requests.video.MessageRequest;
import com.esgi.pa.api.dtos.responses.lobby.GetlobbyMessageResponse;
import com.esgi.pa.api.dtos.responses.video.MessageResponse;
import com.esgi.pa.domain.entities.*;
import com.esgi.pa.domain.enums.StatusMessage;
import com.esgi.pa.domain.enums.VideoStatusEnum;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.server.adapter.MessageAdapter;
import com.esgi.pa.server.adapter.MessagePrivateAdapter;
import com.esgi.pa.server.adapter.VideoCallAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

/**
 * Service de gestion des messages
 */
@Service
@RequiredArgsConstructor
public class MessageService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageAdapter messageAdapter;
    private final VideoCallService videoCallService;
    private final MessagePrivateAdapter messagePrivateAdapter;
    private final ChatService chatService;
    private final LobbyService lobbyService;
    private final UserService userService;

    /**
     * Process le message à transmettre aux utilisateurs dans un lobby
     *
     * @param lobby   lobby auquel le chat appartient
     * @param user    utilisateur à l'origine du message
     * @param message informations relative au message
     * @throws TechnicalNotFoundException si un élément n'est pas trouvé
     */
    public void dispatchMessage(GetlobbyMessageResponse lobby, User user, SendMessageInLobbyRequest message) throws TechnicalNotFoundException {
        lobby.participants().forEach(participant -> {
            if (!Objects.equals(participant.id(), user.getId())) {
                simpMessagingTemplate.convertAndSendToUser(participant.name(), "/private", message);
            }
        });
        Chat chat = chatService.saveChat(lobbyService.getById(lobby.id()));
        String dateString = message.currentDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
        Message message2 = new Message(chat, user, message.message(), dateTime);
        messageAdapter.save(message2);
    }

    /**
     * Process les messages envoyé dans les chats privés
     *
     * @param senderUser   utilisateur à l'origine du message
     * @param receiverUser utilisateur receveur
     * @param message      informations relative au message
     */
    public void dispatchMessagePrivate(User senderUser, User receiverUser, SendMessageInPrivate message) {
        String dateString = message.currentDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
        MessagePrivate message2 = new MessagePrivate(senderUser, receiverUser, message.message(), dateTime, StatusMessage.UNREAD);
        messagePrivateAdapter.save(message2);
    }

    public void dispatchMessageVideo(String to, MessageRequest request, String etat) throws TechnicalNotFoundException {
        simpMessagingTemplate.convertAndSendToUser(to, "/private/video", request);
        Lobby lobby = lobbyService.getById(request.getLobby());
        VideoCall videoCall = new VideoCall();
        Optional<VideoCall> op = videoCallService.findVideoPending(lobby);
        if(op.isPresent())
            videoCall = op.get();
        if(etat =="call") {
            videoCall.setCallFrom(request.getFrom());
            videoCall.setUserToCall(request.getUserToCall());
            videoCall.setName(request.getName());
            videoCall.setSignalData(request.getSignalData());
            videoCall.setLobby(lobby);
            videoCall.setVideoStatusEnum(VideoStatusEnum.START);
            videoCallService.saveVideo(videoCall);
        }else {
            videoCall.setVideoStatusEnum(VideoStatusEnum.LEAVE);
            videoCallService.saveVideo(videoCall);
        }
    }

    public void dispatchMessageVideoCall(CallRequest request) throws TechnicalNotFoundException {
        Lobby lobby = lobbyService.getById(request.getLobby());
        User user = userService.getById(request.getUserConnect());
        lobby.getParticipants().forEach(participant -> {
            if (!Objects.equals(participant.getId(), user.getId())) {
                simpMessagingTemplate.convertAndSendToUser(participant.getName(), "/private/video/call", request);
            }
        });
    }

    public MessageResponse lastInfosDataVideo (MessageRequest request) throws TechnicalNotFoundException {
        Lobby lobby = lobbyService.getById(request.getLobby());
        Optional<VideoCall> videoCall = videoCallService.findVideoPending(lobby);
        return new MessageResponse(videoCall.get().getSignalData(), videoCall.get().getCallFrom(), videoCall.get().getName());
    }
}
