package com.junwoo.devcordbackend.domain.user.dto;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 22.
 */
public record MutualFriendDto(
        Long userId,
        String nickname,
        String profileUrl
) {
}
