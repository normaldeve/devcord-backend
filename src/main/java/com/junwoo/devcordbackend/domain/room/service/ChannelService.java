package com.junwoo.devcordbackend.domain.room.service;

import com.junwoo.devcordbackend.config.exception.ErrorCode;
import com.junwoo.devcordbackend.domain.room.dto.ChannelResponse;
import com.junwoo.devcordbackend.domain.room.dto.CreateChannelRequest;
import com.junwoo.devcordbackend.domain.room.dto.RenameChannelRequest;
import com.junwoo.devcordbackend.domain.room.dto.ServerChannelResponse;
import com.junwoo.devcordbackend.domain.room.entity.ChannelEntity;
import com.junwoo.devcordbackend.domain.room.entity.ChannelType;
import com.junwoo.devcordbackend.domain.room.entity.ServerMemberEntity;
import com.junwoo.devcordbackend.domain.room.exception.ChannelException;
import com.junwoo.devcordbackend.domain.room.repository.ChannelRepository;
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
public class ChannelService {

    private final ValidateChannel validateChannel;
    private final ChannelRepository channelRepository;

    public Long createChannel(Long serverId, Long userId, CreateChannelRequest request) {

        // 사용자 권한 체크
        ServerMemberEntity serverMember = validateChannel.authorizedMember(serverId, userId);

        // 채널 이름 중복 체크
        validateChannel.validateDuplicateChannelName(serverId, request.channelName());

        ChannelEntity channel = ChannelEntity.createChannel(serverId, request);

        ChannelEntity entity = channelRepository.save(channel);

        log.info("[ChannelService] 채널 생성 - serverId: {}, type: {}", entity.getServerId(), entity.getType());

        return entity.getId();
    }

    public void renameChannel(Long serverId, Long channelId, Long userId, RenameChannelRequest request) {
        validateChannel.authorizedMember(serverId, userId);

        ChannelEntity channelEntity = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelException(ErrorCode.CHANNEL_NOT_IN_SERVER));

        validateChannel.validateDuplicateChannelName(serverId, request.newName());

        channelEntity.renameChannel(request.newName());

        log.info("[ChannelService] 채널 이름 변경 - newName: {}, byUser: {}", request.newName(), userId);
    }

    @Transactional(readOnly = true)
    public ServerChannelResponse getChannels(Long serverId, Long userId) {
        List<ChannelEntity> channels =
                channelRepository.findByServerIdOrderByCreatedAtAsc(serverId);

        List<ChannelResponse> textChannels = channels.stream()
                .filter(c -> c.getType() == ChannelType.TEXT)
                .map(ChannelResponse::toSummary)
                .toList();

        List<ChannelResponse> voiceChannels = channels.stream()
                .filter(c -> c.getType() == ChannelType.VOICE)
                .map(ChannelResponse::toSummary)
                .toList();

        return new ServerChannelResponse(textChannels, voiceChannels);
    }
}
