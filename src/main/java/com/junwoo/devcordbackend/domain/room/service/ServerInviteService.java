package com.junwoo.devcordbackend.domain.room.service;

import com.junwoo.devcordbackend.common.exception.ErrorCode;
import com.junwoo.devcordbackend.domain.notification.dto.event.ServerInviteCreatedEvent;
import com.junwoo.devcordbackend.domain.room.entity.channel.ServerInviteEntity;
import com.junwoo.devcordbackend.domain.room.dto.ServerInviteResponse;
import com.junwoo.devcordbackend.domain.room.entity.channel.InviteStatus;
import com.junwoo.devcordbackend.domain.room.entity.channel.ServerEntity;
import com.junwoo.devcordbackend.domain.room.entity.channel.ServerMemberEntity;
import com.junwoo.devcordbackend.domain.room.exception.ServerException;
import com.junwoo.devcordbackend.domain.room.repository.ServerInviteRepository;
import com.junwoo.devcordbackend.domain.room.repository.channel.ServerMemberRepository;
import com.junwoo.devcordbackend.domain.room.repository.channel.ServerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 21.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ServerInviteService {

    private final ServerValidate serverValidate;
    private final ServerRepository serverRepository;
    private final ServerMemberRepository serverMemberRepository;
    private final ServerInviteRepository serverInviteRepository;
    private final ApplicationEventPublisher eventPublisher;

    public void invite(Long serverId, Long inviterId, Long inviteeId) {

        log.info("serverId: {}, inviterId: {}, inviteeId: {}", serverId, inviterId, inviteeId);

        // [검증] 서버 관리자 권한 검증
        serverValidate.validateManagerRight(serverId, inviterId);

        // [검증] 이미 서버에 속한 멤버인지
        serverValidate.validateAlreadyServerMember(serverId, inviteeId);

        // [검증] 초대자에게 중복으로 초대를 보내지 않았는지
        serverValidate.validateDuplicateServerInviteRequest(serverId, inviteeId);

        ServerInviteEntity invite = ServerInviteEntity.createInvite(serverId, inviterId, inviteeId);

        serverInviteRepository.save(invite);

        eventPublisher.publishEvent(new ServerInviteCreatedEvent(invite.getId()));
    }

    public void acceptInvite(Long inviteeId, Long serverId) {
        ServerInviteEntity invite = serverInviteRepository.findByServerIdAndInviteeIdAndStatus(serverId, inviteeId, InviteStatus.PENDING)
                .orElseThrow(() -> new ServerException(ErrorCode.SERVER_INVITE_NOT_FOUND));

        invite.accept();

        ServerMemberEntity member = ServerMemberEntity.createServerMember(serverId, inviteeId);

        serverMemberRepository.save(member);

        log.info("[ServerInviteService] 서버 초대 수락 완료 - inviteeId: {}, serverId: {}", inviteeId, serverId);
    }

    public List<ServerInviteResponse> getMyPendingInvites(Long userId) {

        List<ServerInviteEntity> invites = serverInviteRepository.findByInviteeIdAndStatusOrderByCreatedAtDesc(userId, InviteStatus.PENDING);

        if (invites.isEmpty()) {
            return List.of();
        }

        List<Long> serverIds = invites.stream()
                .map(ServerInviteEntity::getServerId)
                .distinct()
                .toList();

        Map<Long, ServerEntity> serverMap =
                serverRepository.findAllById(serverIds).stream()
                        .collect(Collectors.toMap(ServerEntity::getId, Function.identity()));

        return invites.stream()
                .map(invite -> ServerInviteResponse.from(invite, serverMap.get(invite.getServerId())))
                .toList();
    }
}
