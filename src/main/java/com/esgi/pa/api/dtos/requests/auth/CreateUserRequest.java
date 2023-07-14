package com.esgi.pa.api.dtos.requests.auth;

import com.esgi.pa.domain.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record CreateUserRequest(
    @NotBlank(message = "Name is required")
    String name,
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    String email,
    @NotBlank(message = "Password is required")
    @Size(min = 5, message = "Password must be at least 5 characters long")
    String password,
    @NotNull(message = "Role is required")
    RoleEnum role
) {

}
