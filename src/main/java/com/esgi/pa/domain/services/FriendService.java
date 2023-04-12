package com.esgi.pa.domain.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.esgi.pa.domain.entities.User;
import com.esgi.pa.server.adapter.FriendAdapter;
import com.esgi.pa.server.adapter.UserAdapter;

import antlr.collections.List;
import lombok.RequiredArgsConstructor;
import lombok.val;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendAdapter friendAdapter;
    private final UserAdapter userAdapter;

    public Object add(UUID requesterId, UUID requestedId) {
        return null;
    }
    
    private boolean checkIfFriend(UUID requestedID, UUID requesterID) {
        if (checkUserExists(requestedID)) {
            User requested = userAdapter.findById(requestedID).get();
            for (val friend : requested.getFriends()) {
                if (friend.getId().equals(requesterID))
                    return true;
            }
        } return false;
    }
    
    private boolean checkIfFriendRequestExists(UUID requester, UUID requested) {
        return friendAdapter.findById(requester).isPresent() || friendAdapter.findById(requested).isPresent();
    }

    private boolean checkUserExists(UUID id) {
        return userAdapter.findById(id).isPresent();
    }
}
