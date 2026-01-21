package com.junwoo.devcordbackend.domain.notification.dto;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 21.
 */
public record NotificationTarget(
        String targetType,   // SERVER, DM, USER
        Long targetId,       // serverId, dmRoomId, userId
        Long subTargetId     // channelId, messageId (nullable)
) {}
