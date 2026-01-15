package com.junwoo.devcordbackend.domain.user.dto;

import com.junwoo.devcordbackend.domain.user.entity.UserEntity;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 14.
 */
public record FriendResponse(
        Long userId,
        String nickname,
        String profileUrl,
        boolean isOnline
) {

    public static FriendResponse from(UserEntity user, boolean isOnline) {
        return new FriendResponse(
                user.getId(),
                user.getNickname(),
                user.getProfileUrl(),
                isOnline
        );
    }
}