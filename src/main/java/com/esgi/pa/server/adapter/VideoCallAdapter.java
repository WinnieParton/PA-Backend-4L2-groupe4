package com.esgi.pa.server.adapter;

import com.esgi.pa.domain.entities.Chat;
import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.entities.VideoCall;
import com.esgi.pa.domain.enums.VideoStatusEnum;
import com.esgi.pa.server.PersistenceSpi;
import com.esgi.pa.server.repositories.ChatsRepository;
import com.esgi.pa.server.repositories.VideoCallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Adapter de persistence pour les chats
 */
@Service
@RequiredArgsConstructor
public class VideoCallAdapter implements PersistenceSpi<VideoCall, Long> {
    private final VideoCallRepository videoCallRepository;


    @Override
    public VideoCall save(VideoCall o) {
        return videoCallRepository.save(o);
    }

    @Override
    public List<VideoCall> saveAll(List<VideoCall> oList) {
        return null;
    }

    @Override
    public Optional<VideoCall> findById(Long aLong) {
        return videoCallRepository.findById(aLong);
    }

    @Override
    public List<VideoCall> findAll() {
        return videoCallRepository.findAll();
    }

    @Override
    public boolean removeById(Long aLong) {
        return false;
    }

    @Override
    public boolean removeAll(List<Long> longs) {
        return videoCallRepository.findAll().removeAll(longs);
    }

    public Optional<VideoCall> findByLobbyAndVideoStatusEnum(Lobby lobby, VideoStatusEnum videoStatusEnum){
        return videoCallRepository.findByLobbyAndVideoStatusEnum(lobby,videoStatusEnum);
    }
}
