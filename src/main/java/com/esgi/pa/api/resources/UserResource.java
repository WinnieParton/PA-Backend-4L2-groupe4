package com.esgi.pa.api.resources;

import com.esgi.pa.api.dtos.requests.message.SendMessageInPrivate;
import com.esgi.pa.api.dtos.responses.message.ListMessageInPrivateResponse;
import com.esgi.pa.api.dtos.responses.user.GetUserResponse;
import com.esgi.pa.api.mappers.UserMapper;
import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.ChatService;
import com.esgi.pa.domain.services.MessageService;
import com.esgi.pa.domain.services.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contient les routes relatives aux utilisateurs
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Api(tags = "User API")
public class UserResource {
    private final UserService userService;
    private final ChatService chatService;
    private final MessageService messageService;

    /**
     * Récupère un utilisateur par son nom et son id
     *
     * @param id   id numérique de l'utilisateur
     * @param name nom de l'utilisateur
     * @return les informations relative à un utilisateur
     */
    @GetMapping("{id}/name/{name}")
    public List<GetUserResponse> getUserByUsername(@PathVariable Long id, @PathVariable String name) {
        return UserMapper.toGetUserResponse(
            userService.getByNameAndId(id, name));
    }

    /**
     * Récupère un utilisateur par son id
     *
     * @param id id numérique de l'utilisateur
     * @return informations relative à l'utilisateur
     * @throws TechnicalNotFoundException si l'utilisateur n'est pas trouvé
     */
    @GetMapping("{id}")
    public GetUserResponse getUserById(@PathVariable Long id) throws TechnicalNotFoundException {
        return UserMapper.toGetUserResponse(
            userService.getById(id));
    }

    /**
     * Récupère les chats avec les amis d'un utilisateur
     *
     * @param myUserId id numérique de l'utilisateur
     * @return la liste des chats de l'utilisateur avec ses amis
     * @throws TechnicalNotFoundException si l'utilisateur n'est pas trouvé
     */
    @GetMapping("/chat/list/{myUserId}")
    public List<ListMessageInPrivateResponse> getFriends(@PathVariable Long myUserId) throws TechnicalNotFoundException {
        User user = userService.getById(myUserId);
        return chatService.ListchatPrivateResponse(user);
    }

    /**
     * Permet la récupération des messages privés entre 2 utilisateurs
     *
     * @param myUserId  id numérique de l'envoyeur
     * @param receiveUs id numérique du receveur
     * @return les messages privés entre les 2 utilisateurs
     * @throws TechnicalNotFoundException si un élément n'est pas trouvé
     */
    @GetMapping("/chat/private/{myUserId}/to/{receiveUs}")
    public List<ListMessageInPrivateResponse> processGetPrivateMessage(@PathVariable Long myUserId, @PathVariable Long receiveUs) throws TechnicalNotFoundException {
        return chatService.chatPrivateResponse(
            userService.getById(myUserId),
            userService.getById(receiveUs));
    }

    /**
     * Persiste les messages privés envoyé entre 2 utilisateurs
     *
     * @param message informations relatives à un message privé
     * @throws TechnicalNotFoundException si un élément n'est pas trouvé
     */
    @PostMapping("/private-chat-message")
    public void processSendPrivateMessage(@RequestBody SendMessageInPrivate message) throws TechnicalNotFoundException {
        messageService.dispatchMessagePrivate(
            userService.getById(message.senderUser()),
            userService.getById(message.receiverUser()),
            message);
    }
}
