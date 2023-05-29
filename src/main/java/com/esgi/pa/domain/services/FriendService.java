package com.esgi.pa.domain.services;

import com.esgi.pa.domain.entities.Friend;
import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.enums.RequestStatus;
import com.esgi.pa.domain.exceptions.TechnicalException;
import com.esgi.pa.server.adapter.FriendAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendAdapter friendAdapter;

    public Friend sendRequest(User sender, User receiver) throws TechnicalException {
        if (!checkIfFriend(sender, receiver) && !isSenderReceiver(sender, receiver)) {
            return friendAdapter.save(
                Friend.builder()
                    .user1(sender)
                    .user2(receiver)
                    .build());
        } else throw new TechnicalException(HttpStatus.FOUND, "Sender already friend with user : "+ receiver);
    }

    public List<Friend> getFriendRequestSent(User sender) {
        return friendAdapter.findByUser1(sender);
    }

    public List<Friend> getFriendRequestReceived(User receiver) {
        return friendAdapter.findByUser2(receiver);
    }

    private boolean checkIfFriend(User sender, User receiver) {
        return sender.getFriends().contains(receiver);
    }

    private boolean isSenderReceiver(User sender, User receiver) {
        return sender.equals(receiver);
    }

    public Friend handleRequest(User sender, User receiver, RequestStatus status) throws TechnicalException {
        return switch (status) {
            case ACCEPTED -> acceptRequest(sender, receiver);
            case REJECTED -> rejectRequest(sender, receiver);
            default -> throw new TechnicalException(HttpStatus.FORBIDDEN, "Unexpected error with friend request status : "+ status);
        };
    }

    private Friend acceptRequest(User sender, User receiver) throws TechnicalException {
        Friend friend = friendAdapter.findByUserAndFriend(sender, receiver)
            .orElseThrow(() -> new TechnicalException(HttpStatus.NOT_FOUND, "Friend request not found", sender));
        friendAdapter.save(Friend.builder()
            .user1(receiver)
            .user2(sender)
            .status(RequestStatus.ACCEPTED)
            .build());
        return friendAdapter.save(friend.withStatus(RequestStatus.ACCEPTED));
    }

    private Friend rejectRequest(User sender, User receiver) throws TechnicalException {
        Friend friend = friendAdapter.findByUserAndFriend(sender, receiver)
            .orElseThrow(() -> new TechnicalException(HttpStatus.NOT_FOUND, "Friend request not found", sender));
        return friendAdapter.save(friend.withStatus(RequestStatus.REJECTED));
    }
}
