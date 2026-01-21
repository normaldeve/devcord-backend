package com.junwoo.devcordbackend.domain.message.dto;

import com.junwoo.devcordbackend.domain.message.entity.DirectMessageEntity;

import java.time.LocalDateTime;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 15.
 */
public record DirectMessageResponse(
        Long messageId,
        Long directRoomId,
        Long senderId,
        String senderNickname,
        String senderProfileUrl,
        String content,
        boolean pinned,
        Long replyTo,
        LocalDateTime createdAt
) {
    public static DirectMessageResponse from(DirectMessageEntity message, String senderNickname, String senderProfileUrl) {
        return new DirectMessageResponse(
                message.getId(),
                message.getDirectRoomId(),
                message.getSenderId(),
                senderNickname,
                senderProfileUrl,
                message.getContent(),
                message.isPinned(),
                message.getReplyTo(),
                message.getCreatedAt()
        );
    }
}
