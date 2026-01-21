package com.junwoo.devcordbackend.domain.room.dto;

import com.junwoo.devcordbackend.domain.room.entity.channel.ServerEntity;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 21.
 */
public record CreateServerResponse(
        Long serverId,
        String serverName,
        String iconUrl
) {

    public static CreateServerResponse from(ServerEntity entity) {

        return new CreateServerResponse(entity.getId(), entity.getName(), entity.getIconUrl());

    }
}
