package com.esgi.pa.server.adapter;

import com.esgi.pa.domain.entities.Game;
import com.esgi.pa.domain.entities.Ranking;
import com.esgi.pa.domain.entities.User;
import com.esgi.pa.server.PersistenceSpi;
import com.esgi.pa.server.repositories.RankingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RankingAdapter implements PersistenceSpi<Ranking, Long> {

    private final RankingsRepository rankingsRepository;

    @Override
    public Ranking save(Ranking o) {
        return rankingsRepository.save(o);
    }

    @Override
    public List<Ranking> saveAll(List<Ranking> oList) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveAll'");
    }

    @Override
    public Optional<Ranking> findById(Long id) {
        return rankingsRepository.findById(id);
    }

    @Override
    public List<Ranking> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    public Optional<Ranking> findByGameAndPlayer(Game game, User user){
        return rankingsRepository.findByGameAndPlayer(game, user);
    }

    public List<Ranking> findRankingPlayer(User user){
        return rankingsRepository.findByPlayer(user);
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
