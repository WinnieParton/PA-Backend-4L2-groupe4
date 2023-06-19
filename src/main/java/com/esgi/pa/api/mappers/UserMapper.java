package com.esgi.pa.api.mappers;

import com.esgi.pa.api.dtos.responses.AuthenticationUserResponse;
import com.esgi.pa.api.dtos.responses.GetUserResponse;
import com.esgi.pa.api.dtos.responses.NoFriendsUserResponse;
import com.esgi.pa.domain.entities.User;

import java.util.List;

public interface UserMapper {

    static GetUserResponse toGetUserResponse(User user) {
        return new GetUserResponse(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getRole(),
            user.getFriends().stream().map(UserMapper::toNoFriendsUserResponse).toList());
    }

    static List<GetUserResponse> toGetUserResponse(List<User> entities) {
        return entities.stream()
            .map(UserMapper::toGetUserResponse)
                .toList();
    }

    static NoFriendsUserResponse toNoFriendsUserResponse(User user) {
        return new NoFriendsUserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole());
    }

    static List<NoFriendsUserResponse> toNoFriendsUserResponse(List<User> entities) {
        return entities.stream()
                .map(UserMapper::toNoFriendsUserResponse)
                .toList();
    }

    static AuthenticationUserResponse toAuthenticationUserResponse(String token) {
        return new AuthenticationUserResponse(token);
    }
}
