package com.junwoo.devcordbackend.domain.notification.dto;

import com.junwoo.devcordbackend.domain.notification.entity.NotificationEntity;
import com.junwoo.devcordbackend.domain.notification.entity.NotificationSubType;
import com.junwoo.devcordbackend.domain.notification.entity.NotificationType;
import com.junwoo.devcordbackend.domain.user.dto.UserInfo;

import java.time.LocalDateTime;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 21.
 */
public record NotificationResponse(
        Long notificationId,
        Long senderId,
        String senderNickname,
        String senderProfileImageUrl,
        NotificationType type,
        NotificationSubType subType,
        String content,
        NotificationTarget target,
        boolean read,
        LocalDateTime createdAt
) {

    public static NotificationResponse from(NotificationEntity notification, UserInfo senderInfo) {
        return new NotificationResponse(
                notification.getId(),
                senderInfo != null ? senderInfo.userId(): null,
                senderInfo != null ? senderInfo.nickname() : "System",
                senderInfo != null ? senderInfo.profileUrl() : null,
                notification.getType(),
                notification.getSubType(),
                notification.getContent(),
                new NotificationTarget(
                        notification.getTargetType(),
                        notification.getTargetId(),
                        notification.getSubTargetId()
                ),
                notification.isRead(),
                notification.getCreatedAt()
        );
    }
}
