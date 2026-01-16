package com.junwoo.devcordbackend.domain.room.dto;

import com.junwoo.devcordbackend.domain.room.entity.channel.ChannelEntity;
import com.junwoo.devcordbackend.domain.room.entity.channel.ChannelType;

import java.time.LocalDateTime;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 13.
 */
public record ChannelResponse(
        Long channelId,
        String name,
        ChannelType type,
        LocalDateTime createdAt
) {

    public static ChannelResponse toSummary(ChannelEntity channel) {
        return new ChannelResponse(
                channel.getId(),
                channel.getName(),
                channel.getType(),
                channel.getCreatedAt()
        );
    }
}
