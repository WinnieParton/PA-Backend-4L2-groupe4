package com.esgi.pa.server.adapter;

import com.esgi.pa.domain.entities.Invitation;
import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.entities.User;
import com.esgi.pa.server.PersistenceSpi;
import com.esgi.pa.server.repositories.InvitationsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvitationAdapter implements PersistenceSpi<Invitation, Long> {

    private final InvitationsRepository invitationsRepository;

    @Override
    public Invitation save(Invitation o) {
        return invitationsRepository.save(o);
    }
    @Override
    public List<Invitation> saveAll(List<Invitation> oList) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveAll'");
    }

    @Override
    public Optional<Invitation> findById(Long id) {
        return invitationsRepository.findById(id);
    }

    @Override
    public List<Invitation> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    public List<Invitation> findAllByUser(User user) {
        return invitationsRepository.findAllByUser(user);
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

    public Optional<Invitation> getInvitationByUserAndLobby(User user, Lobby lobby) {
        return invitationsRepository.getInvitationByUserAndLobby(user, lobby);
    }
}
