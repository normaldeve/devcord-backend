package com.junwoo.devcordbackend.domain.room.service;

import com.junwoo.devcordbackend.common.exception.ErrorCode;
import com.junwoo.devcordbackend.domain.room.entity.channel.ServerMemberEntity;
import com.junwoo.devcordbackend.domain.room.entity.channel.ServerRole;
import com.junwoo.devcordbackend.domain.room.exception.ChannelException;
import com.junwoo.devcordbackend.domain.room.exception.ServerException;
import com.junwoo.devcordbackend.domain.room.repository.channel.ChannelRepository;
import com.junwoo.devcordbackend.domain.room.repository.channel.ServerMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 13.
 */
@Service
@RequiredArgsConstructor
public class ValidateChannel {

    private final ServerMemberRepository serverMemberRepository;
    private final ChannelRepository channelRepository;

    public ServerMemberEntity authorizedMember(Long serverId, Long userId) {
        ServerMemberEntity serverMember = serverMemberRepository.findByServerIdAndUserId(serverId, userId)
                .orElseThrow(() -> new ServerException(ErrorCode.CHATTING_SERVER_DENIED));

        if (serverMember.getRole() != ServerRole.CHANNEL_MANAGER) {
            throw new ServerException(ErrorCode.CHATTING_SERVER_DENIED);
        }

        return serverMember;
    }

    public void validateDuplicateChannelName(Long serverId, String channelName) {
        if (channelRepository.existsByServerIdAndName(serverId, channelName)) {
            throw new ChannelException(ErrorCode.DUPLICATE_CHANNEL_NAME);
        }
    }
}
