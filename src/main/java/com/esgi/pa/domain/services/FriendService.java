package com.esgi.pa.domain.services;

import com.esgi.pa.domain.entities.Friend;
import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.enums.RequestStatusEnum;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.server.adapter.FriendAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service de gestion des amis
 */
@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendAdapter friendAdapter;

    /**
     * Envoi une requête d'ami d'un utilisateur à un autre
     *
     * @param sender   envoyeur
     * @param receiver receveur
     * @return la relation d'amitié nouvellement créée
     * @throws TechnicalFoundException si la relation existe déjà
     */
    public Friend sendRequest(User sender, User receiver) throws TechnicalFoundException {
        if (((!checkIfFriend(sender, receiver) && !isSenderReceiver(sender, receiver)) ||
            (!checkIfFriend(receiver, sender) && !isSenderReceiver(receiver, sender))) &&
            !checkIfFriendSended(sender, receiver)) {
            return friendAdapter.save(
                Friend.builder()
                    .user1(sender)
                    .user2(receiver)
                    .build());
        } else throw new TechnicalFoundException("Sender already friend with user : " + receiver);
    }

    /**
     * Récupère les demandes d'ami envoyé
     *
     * @param sender utilisateur envoyeur
     * @return liste des demandes d'ami
     */
    public List<Friend> getFriendRequestSent(User sender) {
        return friendAdapter.findByUser1(sender);
    }

    /**
     * Récupère les demandes d'ami reçu
     *
     * @param receiver utilisateur receveur
     * @return liste des demandes d'ami
     */
    public List<Friend> getFriendRequestReceived(User receiver) {
        return friendAdapter.findByUser2(receiver);
    }

    /**
     * Récupère la liste  d'ami d'un utilisateur
     *
     * @param user utilisateur dont on doit récupérer la liste d'ami
     * @return liste d'ami désiré
     */
    public List<Friend> getFriends(User user) {
        return friendAdapter.findByUser2orUser1(user);
    }

    /**
     * Vérifie si une relation d'amitié existe déjà entre 2 utilisateurs
     *
     * @param sender   utilisateur envoyeur
     * @param receiver utilisateur receveur
     * @return true si la relation existe pour l'envoyeur; else false
     */
    private boolean checkIfFriend(User sender, User receiver) {
        return sender.getFriends().contains(receiver);
    }

    /**
     * Vérifie si la relation d'amitié existe entre 2 utilisateurs
     *
     * @param sender   utilisateur envoyeur
     * @param receiver utilisateur receveur
     * @return true si la relation existe; else false
     */
    private boolean checkIfFriendSended(User sender, User receiver) {
        return friendAdapter.existsByUser1AndUser2(sender, receiver) || friendAdapter.existsByUser1AndUser2(receiver, sender);
    }

    /**
     * Vérifie si l'envoyeur et le receveur sont le même utilisateur
     *
     * @param sender   utilisateur envoyeur
     * @param receiver utilisateur receveur
     * @return true si il s'agit du même utilisateur; else false
     */
    private boolean isSenderReceiver(User sender, User receiver) {
        return sender.equals(receiver);
    }

    /**
     * Dispatch la réponse à la méthode adapté
     *
     * @param sender   utilisateur envoyeur
     * @param receiver utilisateur receveur
     * @param status   statut de la réponse
     * @return la relation d'amitié
     * @throws TechnicalNotFoundException si un élément n'est pas trouvé
     */
    public Friend handleRequest(User sender, User receiver, RequestStatusEnum status) throws TechnicalNotFoundException {
        return switch (status) {
            case ACCEPTED -> acceptRequest(sender, receiver);
            case REJECTED -> rejectRequest(sender, receiver);
            default ->
                throw new TechnicalNotFoundException(HttpStatus.FORBIDDEN, "Unexpected error with friend request status : " + status);
        };
    }

    /**
     * Accepte la demande d'ami
     *
     * @param sender   utilisateur envoyeur
     * @param receiver utilisateur receveur
     * @return la relation d'amitié accepté
     * @throws TechnicalNotFoundException si un élément n'est pas trouvé
     */
    private Friend acceptRequest(User sender, User receiver) throws TechnicalNotFoundException {
        Friend friend = friendAdapter.findByUserAndFriend(sender, receiver)
            .orElseThrow(() -> new TechnicalNotFoundException(HttpStatus.NOT_FOUND, "Friend request not found", sender));
        return friendAdapter.save(friend.withStatus(RequestStatusEnum.ACCEPTED));
    }

    /**
     * Refuse la demande d'ami
     *
     * @param sender   utilisateur envoyeur
     * @param receiver utilisateur receveur
     * @return la relation d'amitié refusé
     * @throws TechnicalNotFoundException si un élément n'est pas trouvé
     */
    private Friend rejectRequest(User sender, User receiver) throws TechnicalNotFoundException {
        Friend friend = friendAdapter.findByUserAndFriend(sender, receiver)
            .orElseThrow(() -> new TechnicalNotFoundException(HttpStatus.NOT_FOUND, "Friend request not found", sender));
        return friendAdapter.save(friend.withStatus(RequestStatusEnum.REJECTED));
    }
}
