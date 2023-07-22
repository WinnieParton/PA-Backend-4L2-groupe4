package com.esgi.pa.domain.services;

import com.esgi.pa.api.dtos.requests.message.SendMessageInLobbyRequest;
import com.esgi.pa.api.dtos.responses.message.ListMessageInPrivateResponse;
import com.esgi.pa.api.dtos.responses.message.ReceiveMessageInLobbyResponse;
import com.esgi.pa.domain.entities.*;
import com.esgi.pa.domain.enums.StatusMessageEnum;
import com.esgi.pa.domain.enums.VideoStatusEnum;
import com.esgi.pa.server.adapter.ChatAdapter;
import com.esgi.pa.server.adapter.LobbyAdapter;
import com.esgi.pa.server.adapter.MessagePrivateAdapter;
import com.esgi.pa.server.adapter.VideoCallAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

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
        return videoCallAdapter.findByLobbyAndVideoStatusEnum(lobby, VideoStatusEnum.START);
    }

}
