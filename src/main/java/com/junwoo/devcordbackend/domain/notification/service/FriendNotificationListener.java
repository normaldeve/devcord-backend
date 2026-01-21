package com.junwoo.devcordbackend.domain.notification.service;

import com.junwoo.devcordbackend.domain.notification.config.SseEmitterManager;
import com.junwoo.devcordbackend.domain.notification.dto.FriendRequestSentEvent;
import com.junwoo.devcordbackend.domain.notification.entity.NotificationEntity;
import com.junwoo.devcordbackend.domain.notification.entity.NotificationSubType;
import com.junwoo.devcordbackend.domain.notification.entity.NotificationType;
import com.junwoo.devcordbackend.domain.notification.repository.NotificationRepository;
import com.junwoo.devcordbackend.domain.user.dto.UserInfo;
import com.junwoo.devcordbackend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 21.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FriendNotificationListener {

    private final UserService userService;
    private final NotificationRepository notificationRepository;
    private final SseEmitterManager sseEmitterManager;

    @Async
    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handleFriendRequestSent(FriendRequestSentEvent event) {

        NotificationEntity notification = NotificationEntity.create(
                event.receiverId(),
                event.requesterId(),
                NotificationType.INVITE,
                NotificationSubType.FRIEND_INVITE,
                "USER",
                event.requesterId(),
                null,
                "새 친구 요청이 도착했습니다."
        );

        UserInfo senderInfo = userService.getUserInfo(event.requesterId());

        notificationRepository.save(notification);

        sseEmitterManager.send(event.receiverId(), notification, senderInfo);
    }
}