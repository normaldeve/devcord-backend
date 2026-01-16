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
@Table(name = "channel_messages")
@NoArgsConstructor
@AllArgsConstructor
public class ChannelMessageEntity extends BaseEntity {

    private Long channelId;

    private Long senderId;

    private String content;

    // 메시지 고정
    private boolean pinned;

    // 특정 메시지에 대한 답장 여부
    private Long replyTo;

    public static ChannelMessageEntity createMessage(Long channelId, Long senderId, SendMessageRequest request) {
        return ChannelMessageEntity.builder()
                .channelId(channelId)
                .senderId(senderId)
                .content(request.content())
                .pinned(false)
                .replyTo(request.replyTo())
                .build();
    }
}
