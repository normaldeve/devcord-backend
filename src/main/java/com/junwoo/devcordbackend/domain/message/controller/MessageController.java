package com.junwoo.devcordbackend.domain.message.controller;

import com.junwoo.devcordbackend.domain.message.dto.*;
import com.junwoo.devcordbackend.domain.message.service.MessageService;
import com.junwoo.devcordbackend.domain.user.dto.UserInfo;
import com.junwoo.devcordbackend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 16.
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class MessageController {

    private final UserService userService;
    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/channels/{channelId}")
    public void sendChannelMessage(
            @DestinationVariable Long channelId,
            @Payload SendMessageRequest request
    ) {
        Long senderId = request.senderId();

        ChannelMessageResponse response = messageService.sendChannelMessage(channelId, senderId, request);

        messagingTemplate.convertAndSend("/topic/channels." + channelId, response);

        log.info("[WS] 채널 메시지 전송 - channelId = {}, senderId = {}", channelId, senderId);
    }

    /**
     * DM 메시지 전송 (STOMP)
     */
    @MessageMapping("/direct/{roomId}")
    public void sendDirectMessage(
            @DestinationVariable Long roomId,
            @Payload SendMessageRequest request
    ) {
        Long senderId = request.senderId();

        DirectMessageResponse response = messageService.sendDirectMessage(roomId, senderId, request);

        messagingTemplate.convertAndSend("/queue/direct." + roomId, response);

        log.info("[WS] DM 전송 - roomId={}, senderId={}", roomId, senderId);
    }

    //===========================[키보드 타이핑 감지]===========================//
    @MessageMapping("/direct/{roomId}/typing/start")
    public void sendTypingEvent(
            @DestinationVariable Long roomId,
            @Payload TypingEventRequest event
    ) {

        Long senderId = event.senderId();

        UserInfo userInfo = userService.getUserInfo(senderId);

        TypingEventResponse response = new TypingEventResponse(senderId, userInfo.nickname(), roomId, true);

        messagingTemplate.convertAndSend("/queue/direct." + roomId + ".typing", response);

        log.info("[WS] 사용자가 타이핑 작업을 시작했습니다");
    }

    @MessageMapping("/direct/{roomId}/typing/stop")
    public void stopTypingEvent(
            @DestinationVariable Long roomId,
            @Payload TypingEventRequest event
    ) {

        Long senderId = event.senderId();

        UserInfo userInfo = userService.getUserInfo(senderId);

        TypingEventResponse response = new TypingEventResponse(senderId, userInfo.nickname(), roomId, false);

        messagingTemplate.convertAndSend("/queue/direct." + roomId + ".typing", response);

        log.info("[WS] 사용자가 타이핑 작업을 멈추었습니다");
    }
}