package com.junwoo.devcordbackend.domain.room.dto;

import com.junwoo.devcordbackend.domain.room.entity.channel.ServerMemberEntity;
import com.junwoo.devcordbackend.domain.room.entity.channel.ServerRole;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 13.
 */
public record ServerMemberResponse(
        Long userId,
        String name,
        boolean isOnline,
        ServerRole role
) {

    public static ServerMemberResponse from(ServerMemberEntity entity, String name, boolean isOnline) {
        return new ServerMemberResponse(
                entity.getUserId(),
                name,
                isOnline,
                entity.getRole()
        );
    }
}
