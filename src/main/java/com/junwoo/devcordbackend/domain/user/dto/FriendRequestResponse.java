package com.junwoo.devcordbackend.domain.user.dto;

import com.junwoo.devcordbackend.domain.friend.entity.FriendEntity;
import com.junwoo.devcordbackend.domain.friend.entity.FriendStatus;
import com.junwoo.devcordbackend.domain.user.entity.UserEntity;

import java.time.LocalDateTime;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 14.
 */
public record FriendRequestResponse(
        Long requestId,
        Long requesterId,
        String nickname,
        String profileUrl,
        FriendStatus status,
        LocalDateTime requestedAt
) {

    public static FriendRequestResponse from(FriendEntity friend, UserEntity user) {
        return new FriendRequestResponse(
                friend.getId(),
                user.getId(),
                user.getNickname(),
                user.getProfileUrl(),
                friend.getStatus(),
                friend.getCreatedAt()
        );
    }
}
