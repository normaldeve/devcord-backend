package com.junwoo.devcordbackend.domain.notification.dto.event;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 21.
 */
public record FriendRequestAcceptedEvent(
        Long accepterId,   // 수락한 사람 (receiver)
        Long requesterId   // 요청 보낸 사람
) {}
