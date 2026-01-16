package com.junwoo.devcordbackend.domain.message.dto;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 15.
 */

/**
 * 이미지도 추후에 보낼 수 있게 수정할 것!
 */
public record SendMessageRequest(
        Long senderId,
        String content,
        Long replyTo
) {
}
