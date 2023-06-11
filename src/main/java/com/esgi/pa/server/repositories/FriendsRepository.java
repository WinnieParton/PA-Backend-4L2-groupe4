package com.esgi.pa.server.repositories;

import com.esgi.pa.domain.entities.Friend;
import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendsRepository extends JpaRepository<Friend, Long> {

    Optional<Friend> findByUser1AndUser2(User user1, User user2);

    List<Friend> findByUser2AndStatus(User user, RequestStatus status);

    List<Friend> findByUser2AndStatusOrUser1AndStatus(User user, RequestStatus status1, User user1, RequestStatus status);

    List<Friend> findByUser1AndStatus(User o, RequestStatus status);

    Boolean existsByUser1AndUser2(User user, User user1);

}
