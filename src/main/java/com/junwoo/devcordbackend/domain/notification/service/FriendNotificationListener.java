package com.junwoo.devcordbackend.domain.notification.service;

import com.junwoo.devcordbackend.domain.notification.config.SseEmitterManager;
import com.junwoo.devcordbackend.domain.notification.dto.event.FriendRequestAcceptedEvent;
import com.junwoo.devcordbackend.domain.notification.dto.event.FriendRequestSentEvent;
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
                "님의 새 친구 요청을 보내셨습니다."
        );

        UserInfo senderInfo = userService.getUserInfo(event.requesterId());

        notificationRepository.save(notification);

        sseEmitterManager.send(event.receiverId(), notification, senderInfo);
    }

    @Async
    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handleFriendRequestAccepted(FriendRequestAcceptedEvent event) {

        NotificationEntity notification = NotificationEntity.create(
                event.requesterId(),
                event.accepterId(),
                NotificationType.INVITE,
                NotificationSubType.FRIEND_ACCEPTED,
                "USER",
                event.accepterId(),
                null,
                "님이 친구 요청을 수락했습니다"
        );

        notificationRepository.save(notification);

        UserInfo senderInfo = userService.getUserInfo(event.requesterId());

        sseEmitterManager.send(event.requesterId(), notification, senderInfo);
    }
}