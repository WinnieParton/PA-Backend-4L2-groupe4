package com.esgi.pa.domain.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.exceptions.FunctionalException;
import com.esgi.pa.domain.exceptions.TechnicalException;
import com.esgi.pa.server.adapter.UserAdapter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final UserAdapter userAdapter;

    public User add(User sender, User receiver) throws TechnicalException, FunctionalException {
        if (!checkIfFriend(sender, receiver)) {
            List<User> friends = sender.getFriends();
            friends.add(receiver);
            return userAdapter.save(
                sender.withFriends(friends));
        } else throw new FunctionalException("Sender already friend with user : %s", receiver);
    }
    
    private boolean checkIfFriend(User sender, User receiver) throws TechnicalException {
            return sender.getFriends().contains(receiver);
    }
}
