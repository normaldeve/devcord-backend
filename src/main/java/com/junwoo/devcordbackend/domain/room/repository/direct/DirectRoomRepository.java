package com.junwoo.devcordbackend.domain.room.repository.direct;

import com.junwoo.devcordbackend.domain.room.dto.DirectRoomListResponse;
import com.junwoo.devcordbackend.domain.room.entity.direct.DirectRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 15.
 */
public interface DirectRoomRepository extends JpaRepository<DirectRoomEntity, Long> {

    /**
     * 두 사용자 간의 DM방 찾기
     */
    @Query("""
        SELECT dr FROM DirectRoomEntity dr
        JOIN DirectRoomMemberEntity drm1 ON drm1.directRoomId = dr.id AND drm1.userId = :userId1
        JOIN DirectRoomMemberEntity drm2 ON drm2.directRoomId = dr.id AND drm2.userId = :userId2
        WHERE drm1.directRoomId = drm2.directRoomId
    """)
    Optional<DirectRoomEntity> findByTwoUsers(Long userId1, Long userId2);

    @Query("""
        SELECT
            new com.junwoo.devcordbackend.domain.room.dto.DirectRoomListResponse(
                dr.id,
                opponent.userId,
                opponentUser.nickname,
                opponentUser.profileUrl,
                msg.content,
                msg.createdAt
            )
        FROM DirectRoomEntity dr
        JOIN DirectRoomMemberEntity me
            ON me.directRoomId = dr.id AND me.userId = :myId
        JOIN DirectRoomMemberEntity opponent
            ON opponent.directRoomId = dr.id AND opponent.userId <> :myId
        JOIN UserEntity opponentUser
            ON opponentUser.id = opponent.userId
        LEFT JOIN DirectMessageEntity msg
            ON msg.id = (
                SELECT m.id
                FROM DirectMessageEntity m
                WHERE m.directRoomId = dr.id
                ORDER BY m.createdAt DESC
                LIMIT 1
            )
        ORDER BY msg.createdAt DESC NULLS LAST
    """)
    List<DirectRoomListResponse> findMyDirectRooms(@Param("myId") Long myId);

}
