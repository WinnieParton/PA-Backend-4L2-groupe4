package com.esgi.pa.server.repositories;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.esgi.pa.domain.entities.Friend;

public interface FriendsRepository extends MongoRepository<UUID, Friend> {}
