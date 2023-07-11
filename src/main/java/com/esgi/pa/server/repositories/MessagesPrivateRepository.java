package com.esgi.pa.server.repositories;

import com.esgi.pa.domain.entities.Message;
import com.esgi.pa.domain.entities.MessagePrivate;
import com.esgi.pa.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessagesPrivateRepository extends JpaRepository<MessagePrivate, Long> {
    List<MessagePrivate> findBySenderOrReceiverOrderByDateDesc(User user, User sender);
    @Query("SELECT mp FROM MessagePrivate mp WHERE mp.date IN (SELECT MAX(m.date) FROM MessagePrivate m WHERE m.sender = :user OR m.receiver = :user GROUP BY CASE WHEN m.sender = :user THEN m.receiver ELSE m.sender END)")
    List<MessagePrivate> findLastMessagesForUser(@Param("user") User user);
}
