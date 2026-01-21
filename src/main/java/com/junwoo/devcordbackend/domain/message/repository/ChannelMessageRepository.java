package com.junwoo.devcordbackend.domain.message.repository;

import com.junwoo.devcordbackend.domain.message.dto.ChannelMessageResponse;
import com.junwoo.devcordbackend.domain.message.entity.ChannelMessageEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 15.
 */
public interface ChannelMessageRepository extends JpaRepository<ChannelMessageEntity, Long> {

    @Query("""
        SELECT new com.junwoo.devcordbackend.domain.message.dto.ChannelMessageResponse(
            m.id,
            m.channelId,
            m.senderId,
            u.nickname,
            u.profileUrl,
            m.content,
            m.pinned,
            m.replyTo,
            m.createdAt
        )
        FROM ChannelMessageEntity m
        JOIN UserEntity u ON u.id = m.senderId
        WHERE m.channelId = :channelId
          AND (:lastId IS NULL OR m.id < :lastId)
        ORDER BY m.id DESC
    """)
    Slice<ChannelMessageResponse> findChannelMessages(
            @Param("channelId") Long channelId,
            @Param("lastId") Long lastMessageId,
            Pageable pageable
    );
}
