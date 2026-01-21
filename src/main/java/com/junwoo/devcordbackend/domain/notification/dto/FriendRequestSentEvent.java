package com.junwoo.devcordbackend.domain.notification.dto;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 21.
 */
public record FriendRequestSentEvent(
        Long requesterId,
        Long receiverId
) {}
