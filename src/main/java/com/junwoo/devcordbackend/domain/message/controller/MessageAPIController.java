package com.junwoo.devcordbackend.domain.message.controller;

import com.junwoo.devcordbackend.domain.message.dto.ChannelMessageResponse;
import com.junwoo.devcordbackend.domain.message.dto.DirectMessageResponse;
import com.junwoo.devcordbackend.domain.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 18.
 */
@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageAPIController {

    private final MessageService messageService;

    @GetMapping("/direct/{roomId}")
    public ResponseEntity<Slice<DirectMessageResponse>> getDirectMessages(
            @PathVariable Long roomId,
            @RequestParam(required = false) Long lastMessageId,
            @RequestParam(defaultValue = "30") int size
    ) {

        return ResponseEntity.ok(messageService.getDirectMessages(roomId, lastMessageId, size));
    }

    @GetMapping("/channel/{channelId}")
    public ResponseEntity<Slice<ChannelMessageResponse>> getChannelMessages(
            @PathVariable Long channelId,
            @RequestParam(required = false) Long lastMessageId,
            @RequestParam(defaultValue = "30") int size
    ) {

        return ResponseEntity.ok(messageService.getChannelMessages(channelId, lastMessageId, size));
    }
}