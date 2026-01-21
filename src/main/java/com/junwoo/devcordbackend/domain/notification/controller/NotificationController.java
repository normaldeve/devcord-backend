package com.junwoo.devcordbackend.domain.notification.controller;

import com.junwoo.devcordbackend.domain.notification.config.SseEmitterManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 21.
 */
@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final SseEmitterManager sseEmitterManager;

    @GetMapping(value = "/subscribe", produces = "text/event-stream")
    public SseEmitter subscribe(@RequestParam Long userId) {

        return sseEmitterManager.connect(userId);
    }
}
