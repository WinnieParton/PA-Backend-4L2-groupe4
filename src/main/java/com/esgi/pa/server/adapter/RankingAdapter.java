package com.esgi.pa.server.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.esgi.pa.domain.entities.Ranking;
import com.esgi.pa.server.PersistenceSpi;
import com.esgi.pa.server.repositories.RankingsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RankingAdapter implements PersistenceSpi<Ranking, Long> {

    private final RankingsRepository rankingsRepository;

    @Override
    public Ranking save(Ranking o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public List<Ranking> saveAll(List<Ranking> oList) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveAll'");
    }

    @Override
    public Optional<Ranking> findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<Ranking> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public boolean removeById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeById'");
    }

    @Override
    public boolean removeAll(List<Long> ids) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeAll'");
    }
}
