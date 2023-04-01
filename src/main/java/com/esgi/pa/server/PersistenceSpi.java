package com.esgi.pa.server;

import java.util.List;
import java.util.Optional;

public interface PersistenceSpi<T, ID> {

    T save(T o);

    List<T> saveInBatch(List<T> oList);

    Optional<T> findById(ID id);

    Optional<List<T>> findAll();
}
