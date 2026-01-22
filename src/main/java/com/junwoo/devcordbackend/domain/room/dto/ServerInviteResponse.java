package com.junwoo.devcordbackend.domain.room.dto;

import com.junwoo.devcordbackend.domain.room.controller.ServerInviteEntity;
import com.junwoo.devcordbackend.domain.room.entity.channel.ServerEntity;
import com.junwoo.devcordbackend.domain.user.dto.UserInfo;

import java.time.LocalDateTime;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 21.
 */
public record ServerInviteResponse(
        Long inviteId,
        Long serverId,
        String serverName,
        String serverIconUrl,
        LocalDateTime invitedAt
) {
    public static ServerInviteResponse from(ServerInviteEntity invite, ServerEntity server) {
        return new ServerInviteResponse(
                invite.getId(),
                server.getId(),
                server.getName(),
                server.getIconUrl(),
                invite.getCreatedAt()
        );
    }
}
