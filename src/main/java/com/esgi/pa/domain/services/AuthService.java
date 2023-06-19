package com.esgi.pa.domain.services;

import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService {

    public Object authorize(User user) throws TechnicalNotFoundException {
        if (Objects.nonNull(user)) {
            return null;
        } else throw new TechnicalNotFoundException(HttpStatus.NOT_FOUND, "Authentication failed user not found");
    }
}
