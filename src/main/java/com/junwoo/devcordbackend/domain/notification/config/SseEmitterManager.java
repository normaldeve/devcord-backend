package com.junwoo.devcordbackend.domain.notification.config;

import com.junwoo.devcordbackend.domain.notification.entity.NotificationEntity;
import com.junwoo.devcordbackend.domain.user.dto.UserInfo;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 21.
 */
public interface SseEmitterManager {

    SseEmitter connect(Long userId);

    void send(Long userId, NotificationEntity notification, UserInfo user);

    void disconnect(Long userId);
}