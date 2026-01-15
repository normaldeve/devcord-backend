package com.junwoo.devcordbackend.domain.user.dto;

import com.junwoo.devcordbackend.domain.user.entity.UserEntity;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 14.
 */
public record UserSearchResponse(
        Long userId,
        String nickname,
        String profileUrl
) {

    public static UserSearchResponse from(UserEntity entity) {
        return new UserSearchResponse(
                entity.getId(),
                entity.getNickname(),
                entity.getProfileUrl()
        );
    }
}
