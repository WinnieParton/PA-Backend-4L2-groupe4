package com.esgi.pa.server.repositories;

import com.esgi.pa.domain.entities.Friend;
import com.esgi.pa.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FriendsRepository extends JpaRepository<Friend, UUID> {

    Optional<Friend> findByUser1AndUser2(User user1, User user2);

    List<Friend> findByUser2(User user);

    List<Friend> findByUser1(User o);

}
