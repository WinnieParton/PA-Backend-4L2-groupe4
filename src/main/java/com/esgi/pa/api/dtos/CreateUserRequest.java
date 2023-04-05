package com.esgi.pa.api.dtos;

import com.esgi.pa.domain.enums.RoleEnum;

public record CreateUserRequest(
    String name,
    String email,
    String password,
    RoleEnum role
) {

}
