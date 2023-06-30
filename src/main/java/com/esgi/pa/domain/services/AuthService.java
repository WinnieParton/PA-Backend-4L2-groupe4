package com.esgi.pa.domain.services;

import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.enums.RoleEnum;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.security.JwtService;
import com.esgi.pa.server.adapter.UserAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final UserAdapter userAdapter;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public String create(String name, String email, String password, RoleEnum role) throws TechnicalFoundException {
        if (userAdapter.findByEmail(email).isEmpty()) {
            User savedUser = userAdapter.save(
                User.builder()
                    .name(name)
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .role(role)
                    .build());
            User user = userAdapter.findByEmail(savedUser.getEmail()).orElseThrow();
            return jwtService.generateToken(
                Map.of("id", user.getId(),
                    "name", user.getName(),
                        "role", user.getRole()),
                user);
        } else {
            throw new TechnicalFoundException("Un compte existe Déjà avec cet email :" + email);
        }
    }

    public String login(String email, String password) throws TechnicalNotFoundException {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                email,
                password));
        User user = userAdapter.findByEmail(email)
            .orElseThrow(() -> new TechnicalNotFoundException(NOT_FOUND, "Username not found with email : " + email));
        return jwtService.generateToken(
            Map.of("id", user.getId(),
                "name", user.getName(),
                    "role", user.getRole()),
            user);
    }
}
