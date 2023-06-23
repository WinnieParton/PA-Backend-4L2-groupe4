package com.esgi.pa.api.mappers;

import com.esgi.pa.api.dtos.responses.friend.AddFriendResponse;
import com.esgi.pa.api.dtos.responses.friend.AnswerFriendRequestResponse;
import com.esgi.pa.api.dtos.responses.friend.GetFriendRequestReceivedResponse;
import com.esgi.pa.api.dtos.responses.friend.GetFriendRequestSentResponse;
import com.esgi.pa.domain.entities.Friend;
import com.esgi.pa.domain.entities.User;

import java.util.ArrayList;
import java.util.List;

public interface FriendMapper {

    static GetFriendRequestSentResponse toGetFriendRequestSentResponse(Friend entity) {
        return new GetFriendRequestSentResponse(
            entity.getId(),
            entity.getUser1().getId(),
            UserMapper.toNoFriendsUserResponse(entity.getUser2()),
            entity.getStatus());
    }

    static List<GetFriendRequestSentResponse> toGetFriendRequestSentResponse(List<Friend> entities) {
        return entities.stream()
            .map(FriendMapper::toGetFriendRequestSentResponse)
            .distinct()
            .toList();
    }
    
    static GetFriendRequestReceivedResponse toFriendRequestReceivedResponse(Friend entity) {
        return new GetFriendRequestReceivedResponse(
            entity.getId(),
            UserMapper.toNoFriendsUserResponse(entity.getUser1()),
            entity.getUser2().getId(),
            entity.getStatus());
    }

    static GetFriendRequestReceivedResponse toFriendRequestResponse(Friend entity, User user) {
        return new GetFriendRequestReceivedResponse(
                entity.getId(),
                UserMapper.toNoFriendsUserResponse(entity.getUser1()),
                entity.getUser2().getId(),
                entity.getStatus());
    }

    static List<GetFriendRequestReceivedResponse> toFriendRequestReceivedResponse(List<Friend> entities) {
        return entities.stream()
            .map(FriendMapper::toFriendRequestReceivedResponse)
            .distinct()
            .toList();
    }

    static List<GetFriendRequestReceivedResponse> toFriendRequestResponse(List<Friend> entities, User user) {
        List<GetFriendRequestReceivedResponse> result = new ArrayList<>();
        entities.forEach(friend -> {
            result.add(new GetFriendRequestReceivedResponse(
                    friend.getId(),
                    UserMapper.toNoFriendsUserResponse(user == friend.getUser1() ? friend.getUser2() : friend.getUser1()),
                    user == friend.getUser1() ?friend.getUser2().getId():friend.getUser1().getId(),
                    friend.getStatus()));
        });

        return result;
    }
    static AnswerFriendRequestResponse toAnswerFriendRequestResponse(Friend entity) {
        return new AnswerFriendRequestResponse(
            entity.getId(),
            UserMapper.toNoFriendsUserResponse(entity.getUser1()),
            entity.getStatus()
        );
    }

    static AddFriendResponse toAddFriendResponse(Friend entity) {
        return new AddFriendResponse(
            entity.getId(),
            entity.getStatus()
        );
    }

}
