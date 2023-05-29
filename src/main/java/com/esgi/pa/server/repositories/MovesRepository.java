package com.esgi.pa.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esgi.pa.domain.entities.Move;

public interface MovesRepository extends JpaRepository<Move, Long> {

}
