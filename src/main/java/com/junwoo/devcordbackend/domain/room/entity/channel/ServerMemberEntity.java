package com.junwoo.devcordbackend.domain.room.entity.channel;

import com.junwoo.devcordbackend.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 13.
 */
@Getter
@Builder
@Entity
@Table(name = "server_members")
@NoArgsConstructor
@AllArgsConstructor
public class ServerMemberEntity extends BaseEntity {

    private Long serverId;

    private Long userId;

    private ServerRole role;

    public static ServerMemberEntity createServerManager(Long serverId, Long userId) {
        return ServerMemberEntity.builder()
                .serverId(serverId)
                .userId(userId)
                .role(ServerRole.CHANNEL_MANAGER)
                .build();
    }
}