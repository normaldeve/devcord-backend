package com.junwoo.devcordbackend.domain.friend.repository;

import com.junwoo.devcordbackend.domain.friend.entity.FriendEntity;
import com.junwoo.devcordbackend.domain.friend.entity.FriendStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 14.
 */
public interface FriendRepository extends JpaRepository<FriendEntity, Long> {

    Optional<FriendEntity> findByRequesterIdAndReceiverId(Long requesterId, Long receiverId);

    Optional<FriendEntity> findByRequesterIdAndReceiverIdOrRequesterIdAndReceiverId(
            Long requesterId, Long receiverId,
            Long receiverId2, Long requesterId2
    );

    List<FriendEntity> findByReceiverIdAndStatusOrderByCreatedAtDesc(Long receiverId, FriendStatus status);

    List<FriendEntity> findByRequesterIdAndStatus(Long requesterId, FriendStatus status);

    @Query("""
            SELECT
                CASE
                    WHEN f.requesterId = :userId THEN f.receiverId
                    ELSE f.requesterId
                  END
                FROM FriendEntity f
                WHERE f.status = 'ACCEPTED'
                AND (f.requesterId = :userId OR f.receiverId = :userId)
            """)
    List<Long> findAcceptedFriendIds(@Param("userId") Long userId);
}
