package com.junwoo.devcordbackend.domain.room.dto;

import java.time.LocalDateTime;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 15.
 */
public record DirectRoomListResponse(
        Long roomId,
        Long toUserId,
        String toUserNickname,
        String toUserProfileUrl,
        String lastMessage,
        LocalDateTime lastMessageAt
) {
}
