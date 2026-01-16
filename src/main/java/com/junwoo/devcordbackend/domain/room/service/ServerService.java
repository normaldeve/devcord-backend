package com.junwoo.devcordbackend.domain.room.service;

import com.junwoo.devcordbackend.domain.room.dto.CreateServerRequest;
import com.junwoo.devcordbackend.domain.room.dto.ServerResponse;
import com.junwoo.devcordbackend.domain.room.entity.channel.ChannelEntity;
import com.junwoo.devcordbackend.domain.room.entity.channel.ServerEntity;
import com.junwoo.devcordbackend.domain.room.entity.channel.ServerMemberEntity;
import com.junwoo.devcordbackend.domain.room.repository.channel.ChannelRepository;
import com.junwoo.devcordbackend.domain.room.repository.channel.ServerMemberRepository;
import com.junwoo.devcordbackend.domain.room.repository.channel.ServerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 13.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ServerService {

    private final ServerRepository serverRepository;
    private final ChannelRepository channelRepository;
    private final ServerMemberRepository serverMemberRepository;

    public Long createServer(Long userId, CreateServerRequest request) {

        ServerEntity server = serverRepository.save(ServerEntity.createServer(request));

        // 기본 텍스트 채널 생성
        ChannelEntity textChannel = ChannelEntity.createTextChannel(server.getId());

        // 기본 보이스 채널 생성
        ChannelEntity voiceChannel = ChannelEntity.createVoiceChannel(server.getId());

        channelRepository.saveAll(List.of(textChannel, voiceChannel));

        ServerMemberEntity creator = ServerMemberEntity.createServerManager(server.getId(), userId);

        serverMemberRepository.save(creator);

        log.info("[ServerService] 서버 생성 완료 - serverId: {}, creatorId: {}", server.getId(), userId);

        return server.getId();

    }

    @Transactional(readOnly = true)
    public List<ServerResponse> getMyServers(Long userId) {

        return serverRepository.findMyServers(userId);
    }
}
