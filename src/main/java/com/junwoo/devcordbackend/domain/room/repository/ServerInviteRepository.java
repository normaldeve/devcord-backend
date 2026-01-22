package com.junwoo.devcordbackend.domain.room.repository;

import com.junwoo.devcordbackend.domain.room.controller.ServerInviteEntity;
import com.junwoo.devcordbackend.domain.room.entity.channel.InviteStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 21.
 */
public interface ServerInviteRepository extends JpaRepository<ServerInviteEntity, Long> {

    Optional<ServerInviteEntity> findByServerIdAndInviteeIdAndStatus(
            Long serverId,
            Long inviteeId,
            InviteStatus status
    );

    List<ServerInviteEntity> findByInviteeIdAndStatusOrderByCreatedAtDesc(
            Long inviteeId,
            InviteStatus status
    );
}
