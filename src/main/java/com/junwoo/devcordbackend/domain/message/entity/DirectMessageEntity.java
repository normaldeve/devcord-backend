package com.junwoo.devcordbackend.domain.message.entity;

import com.junwoo.devcordbackend.common.entity.BaseEntity;
import com.junwoo.devcordbackend.domain.message.dto.SendMessageRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 13.
 */
@Getter
@Builder
@Entity
@Table(name = "direct_messages")
@NoArgsConstructor
@AllArgsConstructor
public class DirectMessageEntity extends BaseEntity {

    private Long directRoomId;

    private Long senderId;

    private String content;

    // 메시지 고정
    private boolean pinned;

    // 특정 메시지에 대한 답장 여부
    private Long replyTo;

    private boolean isRead;

    public static DirectMessageEntity createMessage(Long directRoomId, Long senderId, SendMessageRequest request) {
        return DirectMessageEntity.builder()
                .directRoomId(directRoomId)
                .senderId(senderId)
                .content(request.content())
                .pinned(false)
                .replyTo(request.replyTo())
                .build();
    }
}
