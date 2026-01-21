package com.junwoo.devcordbackend.domain.notification.config;

import com.junwoo.devcordbackend.domain.notification.dto.NotificationResponse;
import com.junwoo.devcordbackend.domain.notification.entity.NotificationEntity;
import com.junwoo.devcordbackend.domain.user.dto.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 21.
 */
@Slf4j
@Component
public class InMemorySseEmitterManager implements SseEmitterManager{

    private static final long DEFAULT_TIMEOUT = 60L * 60L * 1000;

    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    @Override
    public SseEmitter connect(Long userId) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);

        emitters.put(userId, emitter);

        emitter.onCompletion(() -> emitters.remove(userId));
        emitter.onTimeout(() -> emitters.remove(userId));
        emitter.onError(throwable -> emitters.remove(userId));

        log.info("[SSE] 연결됨 - userId: {}", userId);

        return emitter;
    }

    @Override
    public void send(Long userId, NotificationEntity notification, UserInfo user) {
        SseEmitter emitter = emitters.get(userId);

        if (emitter == null) {
            log.info("[SSE] 연결 없음 - userId: {}", userId);
            return;
        }

        try {
            emitter.send(
                    SseEmitter.event()
                            .id(notification.getId().toString())
                            .name("notification")
                            .data(NotificationResponse.from(notification, user))
            );

            log.info("[SSE] 전송 성공 - userId: {}, notification: {}", userId, notification);

        } catch (Exception e) {
            log.warn("[SSE] 전송 실패 - userId: {}, notification: {}", userId, notification, e);
            emitters.remove(userId);
        }
    }

    @Override
    public void disconnect(Long userId) {

        log.info("[SSE] 연결 종료 - userId:{}", userId);
        emitters.remove(userId);
    }
}
