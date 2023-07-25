package com.esgi.pa.domain.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.entities.VideoCall;
import com.esgi.pa.server.adapter.VideoCallAdapter;

import lombok.RequiredArgsConstructor;

/**
 * Service de gestion des chats
 */
@Service
@RequiredArgsConstructor
public class VideoCallService {
    private final VideoCallAdapter videoCallAdapter;
    public VideoCall saveVideo(VideoCall videoCall) {
        return videoCallAdapter.save(videoCall);
    }

    public Optional<VideoCall> findVideoPending(Lobby lobby) {
        return videoCallAdapter.findByLobby(lobby);
    }

    public void deleteVideo(VideoCall videoCall) {
        videoCallAdapter.delete(videoCall);
    }

}
