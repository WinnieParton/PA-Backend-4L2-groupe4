package com.esgi.pa.server.repositories;

import com.esgi.pa.domain.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface de persistence pour les messages
 */
public interface MessagesRepository extends JpaRepository<Message, Long> {

}
