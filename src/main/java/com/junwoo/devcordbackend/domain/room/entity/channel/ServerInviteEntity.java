package com.junwoo.devcordbackend.domain.room.entity.channel;

import com.junwoo.devcordbackend.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 21.
 */
@Getter
@Builder
@Entity
@Table(name = "server_invites")
@NoArgsConstructor
@AllArgsConstructor
public class ServerInviteEntity extends BaseEntity {

    private Long serverId;

    private Long inviterId;

    private Long inviteeId;

    @Enumerated(EnumType.STRING)
    private InviteStatus status;

    public static ServerInviteEntity createInvite(Long serverId, Long inviterId, Long inviteeId) {
        return ServerInviteEntity.builder()
                .serverId(serverId)
                .inviterId(inviterId)
                .inviteeId(inviteeId)
                .status(InviteStatus.PENDING)
                .build();
    }

    public void accept() {
        this.status = InviteStatus.ACCEPTED;
    }
}
