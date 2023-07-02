package com.esgi.pa.server.repositories;

import com.esgi.pa.domain.entities.Message;
import com.esgi.pa.domain.entities.MessagePrivate;
import com.esgi.pa.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessagesPrivateRepository extends JpaRepository<MessagePrivate, Long> {

    List<MessagePrivate> findBySenderOrReceiverOrderByDateDesc(User user);
}
