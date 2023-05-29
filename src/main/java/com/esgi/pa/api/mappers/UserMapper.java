package com.esgi.pa.api.mappers;

import com.esgi.pa.api.dtos.responses.CreateUserResponse;
import com.esgi.pa.api.dtos.responses.GetUserResponse;
import com.esgi.pa.api.dtos.responses.NoFriendsUserResponse;
import com.esgi.pa.domain.entities.User;

import java.util.List;

public interface UserMapper {

    static CreateUserResponse toCreateUserResponse(User entity) {
        return new CreateUserResponse(entity.getId());
    }
    
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

}
