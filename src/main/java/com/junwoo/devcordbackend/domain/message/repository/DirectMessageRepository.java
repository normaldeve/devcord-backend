package com.junwoo.devcordbackend.domain.message.repository;

import com.junwoo.devcordbackend.domain.message.entity.DirectMessageEntity;
import org.springframework.data.domain.Pageable;
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

    /**
     * 커서 기반 페이징 - 최신 메시지부터 조회
     */
    @Query("""
        SELECT m FROM DirectMessageEntity m
        WHERE m.directRoomId = :directRoomId
        AND (:cursor IS NULL OR m.id < :cursor)
        ORDER BY m.id DESC
    """)
    List<DirectMessageEntity> findByDirectRoomIdWithCursor(
            @Param("directRoomId") Long directRoomId,
            @Param("cursor") Long cursor,
            Pageable pageable
    );

    /**
     * 특정 메시지 이후의 새 메시지 조회
     */
    @Query("""
        SELECT m FROM DirectMessageEntity m
        WHERE m.directRoomId = :directRoomId
        AND m.id > :lastMessageId
        ORDER BY m.id ASC
    """)
    List<DirectMessageEntity> findNewMessages(
            @Param("directRoomId") Long directRoomId,
            @Param("lastMessageId") Long lastMessageId
    );

    /**
     * DM방의 총 메시지 개수
     */
    long countByDirectRoomId(Long directRoomId);
}
