package com.esgi.pa.domain.services;

import com.esgi.pa.api.dtos.requests.message.SendMessageInLobbyRequest;
import com.esgi.pa.api.dtos.responses.message.ListMessageInPrivateResponse;
import com.esgi.pa.api.dtos.responses.message.ReceiveMessageInLobbyResponse;
import com.esgi.pa.domain.entities.Chat;
import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.entities.MessagePrivate;
import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.enums.StatusMessageEnum;
import com.esgi.pa.server.adapter.ChatAdapter;
import com.esgi.pa.server.adapter.PrivateMessageAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service de gestion des chats
 */
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatAdapter chatAdapter;
    private final PrivateMessageAdapter privateMessageAdapter;

    /**
     * Persite le chat
     *
     * @param lobby id numérique du lobby
     * @return le chat persisté
     */
    public Chat saveChat(Lobby lobby) {
        return findChatByLobby(lobby)
            .orElseGet(() -> chatAdapter.save(
                new Chat(lobby)
            ));
    }

    /**
     * Cherche un chat par l'id de son lobby
     *
     * @param lobby id numérique du lobby
     * @return un Optional de chat
     */
    public Optional<Chat> findChatByLobby(Lobby lobby) {
        return chatAdapter.findChatByLobby(lobby);
    }

    /**
     * Cherche un chat par l'id de son lobby avec ses messages
     *
     * @param lobby id numérique du lobby
     * @return un Optional de chat avec ses messages
     */
    public Optional<Chat> findChatByLobbyWithMessages(Lobby lobby) {
        return chatAdapter.findChatByLobbyWithMessages(lobby);
    }

    /**
     * process les messages envoyé dans un lobby
     *
     * @param chat                           chat du lobby
     * @param receiveMessageInLobbyResponses information relative au message envoyé
     * @return les messages par lobby
     */
    public Map<Long, List<SendMessageInLobbyRequest>> chatLobbyResponse(Optional<Chat> chat,
                                                                        List<ReceiveMessageInLobbyResponse> receiveMessageInLobbyResponses) {
        Map<Long, List<SendMessageInLobbyRequest>> privateChats = new HashMap<>();
        List<SendMessageInLobbyRequest> messages = new ArrayList<>();

        receiveMessageInLobbyResponses.forEach(message -> {
            messages.add(
                new SendMessageInLobbyRequest(
                    message.senderName(),
                    chat.get().getLobby().getId(),
                    message.message(),
                    chat
                        .get()
                        .getLobby()
                        .getParticipants()
                        .stream()
                        .filter(user -> Objects.equals(user.getId(), message.senderName()))
                        .findFirst()
                        .get()
                        .getName(),
                    chat
                        .get()
                        .getLobby()
                        .getParticipants()
                        .stream()
                        .filter(user -> !Objects.equals(user.getId(), message.senderName()))
                        .findFirst()
                        .get()
                        .getName(),
                    StatusMessageEnum.JOIN,
                    message.currentDate()
                )
            );
        });
        privateChats.put(chat.get().getLobby().getId(), messages);
        return privateChats;
    }

    /**
     * Process les messages privés
     *
     * @param senderUser  utilisateur envoyeur
     * @param receiveUser utilisateur receveur
     * @return listes des messages privés entre 2 utilisateurs
     */
    public List<ListMessageInPrivateResponse> chatPrivateResponse(User senderUser, User receiveUser) {
        List<ListMessageInPrivateResponse> messages = new ArrayList<>();
        List<MessagePrivate> messageSenderPrivateList = privateMessageAdapter.findBySenderOrReceiverOrderByDateDesc(senderUser, senderUser);
        List<MessagePrivate> messageReceiverPrivateList = privateMessageAdapter.findBySenderOrReceiverOrderByDateDesc(receiveUser, receiveUser);
        List<MessagePrivate> mergedList = new ArrayList<>();
        mergedList.addAll(messageSenderPrivateList);
        mergedList.addAll(messageReceiverPrivateList);

        Set<MessagePrivate> uniqueMessages = new HashSet<>(mergedList);
        List<MessagePrivate> messagePrivateList = new ArrayList<>(uniqueMessages);

        messagePrivateList.forEach(msg -> {
            messages.add(
                new ListMessageInPrivateResponse(
                    msg.getSender().getId(),
                    msg.getMessage(),
                    msg.getSender().getName(),
                    msg.getReceiver().getName(),
                    msg.getReceiver().getId(),
                    Objects.equals(msg.getReceiver().getId(), senderUser.getId())
                        ? msg.getSender().getName()
                        : msg.getReceiver().getName(),
                    msg.getStatus(),
                    msg.getDate().toString(),
                    Objects.equals(msg.getSender().getId(), senderUser.getId())
                )
            );
        });

        return sortMessagesByCurrentDateDesc(messages);
    }

    /**
     * Tri les message dans l'ordre chronologique
     *
     * @param messages liste des message à trier
     * @return la liste nouvellement créer
     */
    public List<ListMessageInPrivateResponse> sortMessagesByCurrentDateDesc(List<ListMessageInPrivateResponse> messages) {
        messages.sort(Comparator.comparing(ListMessageInPrivateResponse::currentDate));
        return messages;
    }

    /**
     * Récupère la liste des messages privés d'un utilisateur
     *
     * @param user utilisateur dont on cherche les messages
     * @return listes de messages
     */
    public List<ListMessageInPrivateResponse> listChatPrivateResponse(User user) {
        List<ListMessageInPrivateResponse> messages = new ArrayList<>();
        List<MessagePrivate> messageSenderPrivateList = privateMessageAdapter.findLastMessagesForUser(user);

        messageSenderPrivateList.forEach(msg -> {
            String name = Objects.equals(msg.getReceiver().getId(), user.getId())
                ? msg.getSender().getName()
                : msg.getReceiver().getName();
            if (!Objects.equals(name, user.getName()))
                messages.add(
                    new ListMessageInPrivateResponse(
                        msg.getSender().getId(),
                        msg.getMessage(),
                        msg.getSender().getName(),
                        msg.getReceiver().getName(),
                        msg.getReceiver().getId(),
                        name,
                        msg.getStatus(),
                        msg.getDate().toString(),
                        !Objects.equals(msg.getReceiver().getId(), user.getId())
                    )
                );
        });

        return messages;
    }
}
