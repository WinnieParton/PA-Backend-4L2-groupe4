package com.esgi.pa.domain.services;

import com.esgi.pa.domain.entities.Friend;
import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.enums.RequestStatus;
import com.esgi.pa.domain.exceptions.FunctionalException;
import com.esgi.pa.domain.exceptions.TechnicalException;
import com.esgi.pa.server.adapter.FriendAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendAdapter friendAdapter;

    public Friend sendRequest(User sender, User receiver) throws FunctionalException {
        if (!checkIfFriend(sender, receiver)) {
            return friendAdapter.save(
                Friend.builder()
                    .user(sender)
                    .friend(receiver)
                    .build());
        } else throw new FunctionalException("Sender already friend with user : %s", receiver);
    }

    public List<Friend> getFriendRequest(User sender) {
        return friendAdapter.findByUser(sender);
    }

    private boolean checkIfFriend(User sender, User receiver) {
        return sender.getFriends().contains(receiver);
    }

    public Friend handleRequest(User sender, User receiver, RequestStatus status) throws TechnicalException {
        return switch (status) {
            case ACCEPTED -> acceptRequest(sender, receiver);
            case REJECTED -> rejectRequest(sender, receiver);
            default -> throw new TechnicalException("Unexpected error with friend request status : %s", status);
        };
    }

    private Friend acceptRequest(User sender, User receiver) throws TechnicalException {
        Friend friend = friendAdapter.findByUserAndFriend(sender, receiver)
            .orElseThrow(() -> new TechnicalException("Friend request not found"));
        return friendAdapter.save(friend.withStatus(RequestStatus.ACCEPTED));
    }

    private Friend rejectRequest(User sender, User receiver) throws TechnicalException {
        Friend friend = friendAdapter.findByUserAndFriend(sender, receiver)
            .orElseThrow(() -> new TechnicalException("Friend request not found"));
        return friendAdapter.save(friend.withStatus(RequestStatus.REJECTED));
    }
}
