package com.esgi.pa.api.dtos;

public record UserLoginRequest(
    String email,
    String password
) {

}
