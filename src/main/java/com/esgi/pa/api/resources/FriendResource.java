package com.esgi.pa.api.resources;

import com.esgi.pa.api.dtos.requests.friend.AddFriendRequest;
import com.esgi.pa.api.dtos.requests.friend.AnswerFriendRequest;
import com.esgi.pa.api.dtos.responses.friend.AddFriendResponse;
import com.esgi.pa.api.dtos.responses.friend.AnswerFriendRequestResponse;
import com.esgi.pa.api.dtos.responses.friend.GetFriendRequestsReceivedResponse;
import com.esgi.pa.api.dtos.responses.friend.GetFriendRequestsSentResponse;
import com.esgi.pa.api.mappers.FriendMapper;
import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.FriendService;
import com.esgi.pa.domain.services.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * Contient les routes lié au amis
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/friend")
@Api(tags = "Friend API")
public class FriendResource {

    private final FriendService friendService;
    private final UserService userService;

    /**
     * Permet de récupérer les requêtes envoyer par un utilisateur
     *
     * @param senderId id numérique de l'utilisateur
     * @return la liste des requêtes d'amis envoyées
     * @throws TechnicalNotFoundException si l'utilisateur est introuvable
     */
    @GetMapping("sent/{senderId}")
    public GetFriendRequestsSentResponse getRequestsSent(@PathVariable Long senderId) throws TechnicalNotFoundException {
        return new GetFriendRequestsSentResponse(
            FriendMapper.toGetFriendRequestSentResponse(
                friendService.getFriendRequestSent(
                    userService.getById(senderId))));
    }

    /**
     * Permet de récupérer les requêtes reçus d'un utilisateur
     *
     * @param receiverId id numérique de l'utilisateur
     * @return la liste des requêtes reçu de l'utilisateur
     * @throws TechnicalNotFoundException si l'utilisateur est introuvable
     */
    @GetMapping("received/{receiverId}")
    public GetFriendRequestsReceivedResponse getRequestReceived(@PathVariable Long receiverId) throws TechnicalNotFoundException {
        return new GetFriendRequestsReceivedResponse(
            FriendMapper.toFriendRequestReceivedResponse(
                friendService.getFriendRequestReceived(
                    userService.getById(receiverId))));
    }

    /**
     * Permet de récupérer la liste d'amis d'un utilisateur
     *
     * @param myUserId id numérique de l'utilisateur
     * @return la liste d'amis de l'utilisateur
     * @throws TechnicalNotFoundException si l'utilisateur est introuvable
     */
    @GetMapping("{myUserId}")
    public GetFriendRequestsReceivedResponse getFriends(@PathVariable Long myUserId) throws TechnicalNotFoundException {
        User user = userService.getById(myUserId);
        return new GetFriendRequestsReceivedResponse(
            FriendMapper.toFriendRequestResponse(friendService.getFriends(user), user));
    }

    /**
     * Permet de répondre à une demande d'ami
     *
     * @param receiver id numérique de l'utilisateur répondant
     * @param request  id numérique de l'envoyeur et le statut de la réponse
     * @return le nouveau statut de la relation d'amitié
     * @throws TechnicalNotFoundException si un utilisateur est introuvable
     */
    @PutMapping("{receiver}/answer")
    @ResponseStatus(OK)
    public AnswerFriendRequestResponse answerRequest(@PathVariable Long receiver, @Valid @RequestBody AnswerFriendRequest request) throws TechnicalNotFoundException {
        return FriendMapper.toAnswerFriendRequestResponse(
            friendService.handleRequest(
                userService.getById(request.sender()),
                userService.getById(receiver),
                request.status()));
    }

    /**
     * Traite les requêtes d'ajout d'ami
     *
     * @param receiver id numérique de l'utilisateur ajouté
     * @param request  contient l'id numérique de l'utilisateur ajouteur
     * @return le nouveau statut de la relation d'amitié
     * @throws TechnicalFoundException    si la relation existe déjà
     * @throws TechnicalNotFoundException si un utilisateur est introuvable
     */
    @PostMapping("{receiver}")
    @ResponseStatus(CREATED)
    public AddFriendResponse add(@PathVariable Long receiver, @Valid @RequestBody AddFriendRequest request) throws TechnicalFoundException, TechnicalNotFoundException {
        return FriendMapper.toAddFriendResponse(
            friendService.sendRequest(
                userService.getById(request.sender()),
                userService.getById(receiver)));
    }
}
