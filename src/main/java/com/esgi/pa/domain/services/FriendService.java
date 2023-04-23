package com.esgi.pa.domain.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.esgi.pa.domain.entities.Friend;
import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.enums.FriendRequestStatus;
import com.esgi.pa.domain.exceptions.FunctionalException;
import com.esgi.pa.domain.exceptions.TechnicalException;
import com.esgi.pa.server.adapter.FriendAdapter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendAdapter friendAdapter;

    public Friend sendRequest(User sender, User receiver) throws TechnicalException, FunctionalException {
        if (!checkIfFriend(sender, receiver)) {
            List<User> friends = sender.getFriends();
            friends.add(receiver);
            return friendAdapter.save(
                Friend.builder()
                .user(sender)
                .friend(receiver)
                .build());
        } else throw new FunctionalException("Sender already friend with user : %s", receiver);
    }
    
    private boolean checkIfFriend(User sender, User receiver) {
            return sender.getFriends().contains(receiver);
    }

    public Friend handleRequest(User sender, User receiver, FriendRequestStatus status) throws TechnicalException {
        switch(status) {
            case ACCEPTED:
                return acceptRequest(sender, receiver);
            case REJECTED:
                return rejectRequest(sender, receiver);
            default:
                throw new TechnicalException("Unexpected error with friend request status : %s", status);
        }
    }

    private Friend acceptRequest(User sender, User receiver) throws TechnicalException {
        Friend friend = friendAdapter.findByUserAndFriend(sender, receiver)
            .orElseThrow(() -> new TechnicalException("Friend request not found"));
        return friendAdapter.save(friend.withStatus(FriendRequestStatus.ACCEPTED));
    }

    private Friend rejectRequest(User sender, User receiver) throws TechnicalException {
        Friend friend = friendAdapter.findByUserAndFriend(sender, receiver)
            .orElseThrow(() -> new TechnicalException("Friend request not found"));
        return friendAdapter.save(friend.withStatus(FriendRequestStatus.REJECTED));

    }
}
