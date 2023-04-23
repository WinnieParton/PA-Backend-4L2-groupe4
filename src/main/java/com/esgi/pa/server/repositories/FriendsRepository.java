package com.esgi.pa.server.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esgi.pa.domain.entities.Friend;
import com.esgi.pa.domain.entities.User;

public interface FriendsRepository extends JpaRepository<Friend, UUID> {

    Optional<Friend> findByUserAndFriend(User user, User friend);

    List<Friend> findByUser(User o);

}
