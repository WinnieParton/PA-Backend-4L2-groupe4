package com.esgi.pa.domain.services;

import java.util.Objects;
import java.util.Optional;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.esgi.pa.api.dtos.requests.message.SendMessageInLobbyRequest;
import com.esgi.pa.api.dtos.requests.message.SendMessageInPrivate;
import com.esgi.pa.api.dtos.requests.video.CallRequest;
import com.esgi.pa.api.dtos.requests.video.MessageRequest;
import com.esgi.pa.api.dtos.responses.lobby.GetlobbyMessageResponse;
import com.esgi.pa.domain.entities.Chat;
import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.entities.Message;
import com.esgi.pa.domain.entities.MessagePrivate;
import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.entities.VideoCall;
import com.esgi.pa.domain.enums.StatusMessagePrivateEnum;
import com.esgi.pa.domain.enums.VideoStatusEnum;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.server.adapter.MessageAdapter;
import com.esgi.pa.server.adapter.MessagePrivateAdapter;

import lombok.RequiredArgsConstructor;

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
        Message message2 = new Message(chat, user, message.message(),  message.currentDate());
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
        System.out.println("ddddddddddddddd  "+ message.currentDate());
        MessagePrivate message2 = new MessagePrivate(senderUser, receiverUser, message.message(),  message.currentDate(), StatusMessagePrivateEnum.UNREAD);
        messagePrivateAdapter.save(message2);
        simpMessagingTemplate.convertAndSendToUser(receiverUser.getName(), "/private/message-friend", message);
    }

    public void dispatchMessageVideo(String to, MessageRequest request, String etat) throws TechnicalNotFoundException {
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
            simpMessagingTemplate.convertAndSendToUser(to, "/private/video", request);
        }else{
            simpMessagingTemplate.convertAndSendToUser(to, "/private/video/accepted", request);
        }

    }

    public void dispatchMessageVideoCall(CallRequest request, String etat) throws TechnicalNotFoundException {
        if(etat == "start") {
            Lobby lobby = lobbyService.getById(request.getLobby());
            User user = userService.getById(request.getUserConnect());
            lobby.getParticipants().forEach(participant -> {
                if (!Objects.equals(participant.getId(), user.getId())) {
                    simpMessagingTemplate.convertAndSendToUser(participant.getName(), "/private/video/call", request);
                }
            });
        }else{
            Lobby lobby = lobbyService.getById(request.getLobby());
            User user = userService.getById(request.getUserConnect());
            lobby.getParticipants().forEach(participant -> {
                if (!Objects.equals(participant.getId(), user.getId())) {
                    simpMessagingTemplate.convertAndSendToUser(participant.getName(), "/private/video/call/leave", request);
                }
            });
            Optional<VideoCall> op = videoCallService.findVideoPending(lobby);
            if(op.isPresent()) {
                videoCallService.deleteVideo(op.get());
            }
        }
    }
}
