package com.junwoo.devcordbackend.domain.message.controller;

import com.junwoo.devcordbackend.domain.auth.security.userdetail.DevcordUserDetails;
import com.junwoo.devcordbackend.domain.message.dto.ChannelMessageResponse;
import com.junwoo.devcordbackend.domain.message.dto.DirectMessageResponse;
import com.junwoo.devcordbackend.domain.message.dto.SendMessageRequest;
import com.junwoo.devcordbackend.domain.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
}
