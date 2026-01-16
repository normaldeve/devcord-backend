package com.junwoo.devcordbackend.domain.room.dto;

import java.util.List;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 13.
 */
public record ServerChannelResponse(
        List<ChannelResponse> textChannels,
        List<ChannelResponse> voiceChannels
) {
}
