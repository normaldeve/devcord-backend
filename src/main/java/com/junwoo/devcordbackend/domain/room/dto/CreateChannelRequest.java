package com.junwoo.devcordbackend.domain.room.dto;

import com.junwoo.devcordbackend.domain.room.entity.channel.ChannelType;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 13.
 */
public record CreateChannelRequest(
        String channelName,
        ChannelType channelType
) {
}