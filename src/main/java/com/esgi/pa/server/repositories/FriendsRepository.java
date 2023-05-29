package com.esgi.pa.server.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esgi.pa.domain.entities.Friend;
import com.esgi.pa.domain.entities.User;

public interface FriendsRepository extends JpaRepository<Friend, Long> {

    Optional<Friend> findByUser1AndUser2(User user1, User user2);

    List<Friend> findByUser2(User user);

    List<Friend> findByUser1(User o);

}
