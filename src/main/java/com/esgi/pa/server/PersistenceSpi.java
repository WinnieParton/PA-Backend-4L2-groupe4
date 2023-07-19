package com.esgi.pa.server;

import java.util.List;
import java.util.Optional;

/**
 * Interface de persistence pour tous les adapters
 */
public interface PersistenceSpi<T, ID> {

    T save(T o);

    List<T> saveAll(List<T> oList);

    Optional<T> findById(ID id);

    List<T> findAll();

    boolean removeById(ID id);

    boolean removeAll(List<ID> ids);
}
