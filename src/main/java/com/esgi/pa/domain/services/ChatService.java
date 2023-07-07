package com.esgi.pa.domain.services;

import com.esgi.pa.api.dtos.requests.message.SendMessageInLobbyRequest;
import com.esgi.pa.api.dtos.requests.message.SendMessageInPrivate;
import com.esgi.pa.api.dtos.responses.message.ListMessageInPrivateResponse;
import com.esgi.pa.api.dtos.responses.message.ReceiveMessageInLobbyResponse;
import com.esgi.pa.domain.entities.Chat;
import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.entities.MessagePrivate;
import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.enums.StatusMessageEnum;
import com.esgi.pa.server.adapter.ChatAdapter;
import com.esgi.pa.server.repositories.MessagesPrivateRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

  private final ChatAdapter chatAdapter;
  private final MessagesPrivateRepository messagesPrivateRepository;

  public Chat saveChat(Lobby lobby) {
    Optional<Chat> chat = findChatByLobby(lobby);
    if (chat.isPresent()) return chat.get(); else return chatAdapter.save(
      new Chat(lobby)
    );
  }

  public Optional<Chat> findChatByLobby(Lobby lobby) {
    return chatAdapter.findChatByLobby(lobby);
  }

  public Optional<Chat> findChatByLobbyWithMessages(Lobby lobby) {
    return chatAdapter.findChatByLobbyWithMessages(lobby);
  }

  public Map<Long, List<SendMessageInLobbyRequest>> chatLobbyResponse(
    Optional<Chat> chat,
    List<ReceiveMessageInLobbyResponse> receiveMessageInLobbyResponses
  ) {
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
            .filter(user -> user.getId() == message.senderName())
            .findFirst()
            .get()
            .getName(),
          chat
            .get()
            .getLobby()
            .getParticipants()
            .stream()
            .filter(user -> user.getId() != message.senderName())
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

  public Map<Long, List<SendMessageInPrivate>> chatPrivateResponse(
    User senderUser,
    User receiveUser
  ) {
    Map<Long, List<SendMessageInPrivate>> privateChats = new HashMap<>();
    List<SendMessageInPrivate> messages = new ArrayList<>();
    List<MessagePrivate> messageSenderPrivateList = messagesPrivateRepository.findBySenderOrReceiverOrderByDateDesc(
      senderUser,
      senderUser
    );
    List<MessagePrivate> messageReceiverPrivateList = messagesPrivateRepository.findBySenderOrReceiverOrderByDateDesc(
      receiveUser,
      receiveUser
    );
    List<MessagePrivate> mergedList = new ArrayList<>();
    mergedList.addAll(messageSenderPrivateList);
    mergedList.addAll(messageReceiverPrivateList);

    Set<MessagePrivate> uniqueMessages = new HashSet<>(mergedList);
    List<MessagePrivate> messagePrivateList = new ArrayList<>(uniqueMessages);

    messagePrivateList.forEach(msg -> {
      messages.add(
        new SendMessageInPrivate(
          msg.getSender().getId(),
          msg.getMessage(),
          msg.getSender().getName(),
          msg.getReceiver().getName(),
          msg.getReceiver().getId(),
          msg.getStatus(),
          msg.getDate().toString(),
          msg.getSender().getId() == senderUser.getId() ? true : false
        )
      );
    });
    privateChats.put(senderUser.getId(), messages);

    return privateChats;
  }

  public List<ListMessageInPrivateResponse> ListchatPrivateResponse(User user) {
    List<ListMessageInPrivateResponse> messages = new ArrayList<>();
    List<MessagePrivate> messageSenderPrivateList = messagesPrivateRepository.findLastMessagesForUser(
      user
    );

    messageSenderPrivateList.forEach(msg -> {
      messages.add(
        new ListMessageInPrivateResponse(
          msg.getSender().getId(),
          msg.getMessage(),
          msg.getSender().getName(),
          msg.getReceiver().getName(),
          msg.getReceiver().getId(),
          msg.getReceiver().getId() == user.getId()
            ? msg.getSender().getName()
            : msg.getReceiver().getName(),
          msg.getStatus(),
          msg.getDate().toString(),
          msg.getReceiver().getId() == user.getId() ? false : true
        )
      );
    });

    return messages;
  }
}
