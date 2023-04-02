package com.esgi.pa.server;

import java.util.List;
import java.util.Optional;

public interface PersistenceSpi<T, ID> {

    T save(T o);

    List<T> saveAll(List<T> oList);

    Optional<T> findById(ID id);

    Optional<List<T>> findAll();

    void removeById(ID id);

    void removeAll(List<ID> ids);
}
