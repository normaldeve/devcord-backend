package com.junwoo.devcordbackend.domain.message.dto;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 18.
 */
public record TypingEventResponse(
        Long userId,
        String userNickname,
        Long roomId,
        boolean isTyping
) {}
