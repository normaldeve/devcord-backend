package com.junwoo.devcordbackend.domain.user.repository;

import com.junwoo.devcordbackend.domain.user.entity.FriendEntity;
import com.junwoo.devcordbackend.domain.user.entity.FriendStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 14.
 */
public interface FriendRepository extends JpaRepository<FriendEntity, Long> {

    boolean existsByRequesterIdAndReceiverId(Long requesterId, Long receiverId);

    Optional<FriendEntity> findByRequesterIdAndReceiverId(Long requesterId, Long receiverId);

    Optional<FriendEntity> findByRequesterIdAndReceiverIdOrRequesterIdAndReceiverId(
            Long requesterId, Long receiverId,
            Long receiverId2, Long requesterId2
    );

    List<FriendEntity> findByReceiverIdAndStatusOrderByCreatedAtDesc(Long receiverId, FriendStatus status);

    List<FriendEntity> findByRequesterIdAndStatus(Long requesterId, FriendStatus status);
}
