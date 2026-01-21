package com.junwoo.devcordbackend.domain.notification.entity;

import com.junwoo.devcordbackend.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 21.
 */
@Getter
@Builder
@Entity
@Table(name = "notifications")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class NotificationEntity extends BaseEntity {

    private Long receiverId;

    private Long senderId;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Enumerated(EnumType.STRING)
    private NotificationSubType subType;

    // 이동 정보 사용
    private String targetType;
    private Long targetId;
    private Long subTargetId;

    private String content;

    private boolean isRead;

    public static NotificationEntity create(
            Long receiverId,
            Long senderId,
            NotificationType type,
            NotificationSubType subType,
            String targetType,
            Long targetId,
            Long subTargetId,
            String content
    ) {
        return NotificationEntity.builder()
                .receiverId(receiverId)
                .senderId(senderId)
                .type(type)
                .subType(subType)
                .targetType(targetType)
                .targetId(targetId)
                .subTargetId(subTargetId)
                .content(content)
                .isRead(false)
                .build();
    }
}
