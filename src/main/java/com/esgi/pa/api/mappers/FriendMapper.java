package com.esgi.pa.api.mappers;

import com.esgi.pa.api.dtos.responses.AddFriendResponse;
import com.esgi.pa.api.dtos.responses.AnswerFriendRequestResponse;
import com.esgi.pa.api.dtos.responses.GetFriendRequestReceivedResponse;
import com.esgi.pa.api.dtos.responses.GetFriendRequestSentResponse;
import com.esgi.pa.domain.entities.Friend;

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
            .toList();
    }
    
    static GetFriendRequestReceivedResponse toFriendRequestReceivedResponse(Friend entity) {
        return new GetFriendRequestReceivedResponse(
            entity.getId(),
            UserMapper.toNoFriendsUserResponse(entity.getUser1()),
            entity.getUser2().getId(),
            entity.getStatus());
    }

    static List<GetFriendRequestReceivedResponse> toFriendRequestReceivedResponse(List<Friend> entities) {
        return entities.stream()
            .map(FriendMapper::toFriendRequestReceivedResponse)
            .toList();
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
