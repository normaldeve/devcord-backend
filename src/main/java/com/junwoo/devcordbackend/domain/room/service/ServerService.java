package com.junwoo.devcordbackend.domain.room.service;

import com.junwoo.devcordbackend.domain.image.ImageDirectory;
import com.junwoo.devcordbackend.domain.image.ImageUploader;
import com.junwoo.devcordbackend.domain.room.dto.CreateServerRequest;
import com.junwoo.devcordbackend.domain.room.dto.CreateServerResponse;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    private final ImageUploader imageUploader;
    private final ServerRepository serverRepository;
    private final ChannelRepository channelRepository;
    private final ServerMemberRepository serverMemberRepository;

    public CreateServerResponse createServer(Long userId, CreateServerRequest request, MultipartFile file) {

        String iconUrl = null;

        if (file != null) {
            iconUrl = imageUploader.upload(file, ImageDirectory.SERVER);
        }

        ServerEntity server = serverRepository.save(ServerEntity.createServer(request, iconUrl));

        // 기본 텍스트 채널 생성
        ChannelEntity textChannel = ChannelEntity.createTextChannel(server.getId());

        // 기본 보이스 채널 생성
        ChannelEntity voiceChannel = ChannelEntity.createVoiceChannel(server.getId());

        channelRepository.saveAll(List.of(textChannel, voiceChannel));

        ServerMemberEntity creator = ServerMemberEntity.createServerManager(server.getId(), userId);

        serverMemberRepository.save(creator);

        log.info("[ServerService] 서버 생성 완료 - serverId: {}, creatorId: {}", server.getId(), userId);

        return CreateServerResponse.from(server);

    }

    @Transactional(readOnly = true)
    public List<ServerResponse> getMyServers(Long userId) {

        return serverRepository.findMyServers(userId);
    }

    @Transactional(readOnly = true)
    public Set<Long> findCommonServerIds(Long viewerId, Long targetUserId) {

        Set<Long> viewerServers = serverMemberRepository.findByUserId(viewerId)
                .stream()
                .map(ServerMemberEntity::getServerId)
                .collect(Collectors.toSet());

        Set<Long> targetServers = serverMemberRepository.findByUserId(targetUserId)
                .stream()
                .map(ServerMemberEntity::getServerId)
                .collect(Collectors.toSet());

        viewerServers.retainAll(targetServers);

        return viewerServers;
    }
}
