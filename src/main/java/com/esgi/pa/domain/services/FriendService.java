package com.esgi.pa.domain.services;

import com.esgi.pa.domain.entities.Friend;
import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.enums.RequestStatus;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.server.adapter.FriendAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendAdapter friendAdapter;

    public Friend sendRequest(User sender, User receiver) throws TechnicalFoundException {
        if ((!checkIfFriend(sender, receiver) && !isSenderReceiver(sender, receiver)) ||
                (!checkIfFriend(receiver, sender) && !isSenderReceiver(receiver, sender))) {

            return friendAdapter.save(
                Friend.builder()
                    .user1(sender)
                    .user2(receiver)
                    .build());
        } else throw new TechnicalFoundException("Sender already friend with user : "+ receiver);
    }

    public List<Friend> getFriendRequestSent(User sender) {
        return friendAdapter.findByUser1(sender);
    }

    public List<Friend> getFriendRequestReceived(User receiver) {
        return friendAdapter.findByUser2(receiver);
    }

    public List<Friend> getFriends(User user) {
        return friendAdapter.findByUser2orUser1(user);
    }

    private boolean checkIfFriend(User sender, User receiver) {
        return sender.getFriends().contains(receiver);
    }
    private boolean checkIfFriendSended(User sender, User receiver) {
        return sender.getFriends().contains(receiver);
    }
    private boolean isSenderReceiver(User sender, User receiver) {
        return sender.equals(receiver);
    }

    public Friend handleRequest(User sender, User receiver, RequestStatus status) throws TechnicalNotFoundException {
        return switch (status) {
            case ACCEPTED -> acceptRequest(sender, receiver);
            case REJECTED -> rejectRequest(sender, receiver);
            default -> throw new TechnicalNotFoundException(HttpStatus.FORBIDDEN, "Unexpected error with friend request status : "+ status);
        };
    }

    private Friend acceptRequest(User sender, User receiver) throws TechnicalNotFoundException {
        Friend friend = friendAdapter.findByUserAndFriend(sender, receiver)
            .orElseThrow(() -> new TechnicalNotFoundException(HttpStatus.NOT_FOUND, "Friend request not found", sender));
        friendAdapter.save(Friend.builder()
            .user1(receiver)
            .user2(sender)
            .status(RequestStatus.ACCEPTED)
            .build());
        return friendAdapter.save(friend.withStatus(RequestStatus.ACCEPTED));
    }

    private Friend rejectRequest(User sender, User receiver) throws TechnicalNotFoundException {
        Friend friend = friendAdapter.findByUserAndFriend(sender, receiver)
            .orElseThrow(() -> new TechnicalNotFoundException(HttpStatus.NOT_FOUND, "Friend request not found", sender));
        return friendAdapter.save(friend.withStatus(RequestStatus.REJECTED));
    }
}
