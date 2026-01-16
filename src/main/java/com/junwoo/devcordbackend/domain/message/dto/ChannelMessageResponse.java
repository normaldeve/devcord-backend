package com.junwoo.devcordbackend.domain.message.dto;

import com.junwoo.devcordbackend.domain.message.entity.ChannelMessageEntity;

import java.time.LocalDateTime;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 15.
 */
public record ChannelMessageResponse(
        Long messageId,
        Long channelId,
        Long senderId,
        String senderNickname,
        String senderProfileUrl,
        String content,
        boolean pinned,
        Long replyTo,
        LocalDateTime createdAt
) {

    public static ChannelMessageResponse from(ChannelMessageEntity message, String senderNickname, String senderProfileUrl) {
        return new ChannelMessageResponse(
                message.getId(),
                message.getChannelId(),
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
