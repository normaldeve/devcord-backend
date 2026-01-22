package com.junwoo.devcordbackend.domain.room.service;

import com.junwoo.devcordbackend.common.exception.ErrorCode;
import com.junwoo.devcordbackend.domain.room.entity.channel.InviteStatus;
import com.junwoo.devcordbackend.domain.room.entity.channel.ServerMemberEntity;
import com.junwoo.devcordbackend.domain.room.entity.channel.ServerRole;
import com.junwoo.devcordbackend.domain.room.exception.ServerException;
import com.junwoo.devcordbackend.domain.room.repository.ServerInviteRepository;
import com.junwoo.devcordbackend.domain.room.repository.channel.ServerMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 21.
 */
@Component
@RequiredArgsConstructor
public class ServerValidate {

    private final ServerMemberRepository serverMemberRepository;
    private final ServerInviteRepository serverInviteRepository;

    public void validateManagerRight(Long serverId, Long userId) {
        ServerMemberEntity serverMember = serverMemberRepository.findByServerIdAndUserId(serverId, userId)
                .orElseThrow(() -> new ServerException(ErrorCode.NOT_A_SERVER_MEMBER));

        if (serverMember.getRole() != ServerRole.CHANNEL_MANAGER) {
            throw new ServerException(ErrorCode.CHATTING_SERVER_DENIED);
        }
    }

    public void validateAlreadyServerMember(Long serverId, Long userId) {
        if (serverMemberRepository.existsByServerIdAndUserId(serverId, userId)) {
            throw new ServerException(ErrorCode.ALREADY_SERVER_MEMBER);
        }
    }

    public void validateDuplicateServerInviteRequest(Long serverId, Long inviteeId) {
        serverInviteRepository.findByServerIdAndInviteeIdAndStatus(serverId, inviteeId, InviteStatus.PENDING)
                .ifPresent(
                        inv -> {
                            throw new ServerException(ErrorCode.DUPLICATE_SERVER_INVITE);
                        }
                );
    }
}
