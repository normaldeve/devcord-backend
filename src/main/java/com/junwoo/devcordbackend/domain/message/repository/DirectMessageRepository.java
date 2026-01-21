package com.junwoo.devcordbackend.domain.message.repository;

import com.junwoo.devcordbackend.domain.message.dto.DirectMessageResponse;
import com.junwoo.devcordbackend.domain.message.entity.DirectMessageEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 15.
 */
public interface DirectMessageRepository extends JpaRepository<DirectMessageEntity, Long> {

    @Query("""
    SELECT new com.junwoo.devcordbackend.domain.message.dto.DirectMessageResponse(
        m.id,
        m.directRoomId,
        m.senderId,
        u.nickname,
        u.profileUrl,
        m.content,
        m.pinned,
        m.replyTo,
        m.createdAt
    )
    FROM DirectMessageEntity m
    JOIN UserEntity u ON u.id = m.senderId
    WHERE m.directRoomId = :roomId
      AND (:lastId IS NULL OR m.id < :lastId)
    ORDER BY m.id DESC
""")
    Slice<DirectMessageResponse> findMessages(
            @Param("roomId") Long roomId,
            @Param("lastId") Long lastId,
            Pageable pageable
    );
}
