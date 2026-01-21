package com.junwoo.devcordbackend.domain.user.dto;

import com.junwoo.devcordbackend.domain.user.entity.UserEntity;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 18.
 */
public record UserInfo(
        Long userId,
        String nickname,
        String profileUrl
) {

    public static UserInfo from(UserEntity user) {
        return new UserInfo(user.getId(), user.getNickname(), user.getProfileUrl());
    }
}
