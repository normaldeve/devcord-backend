package com.junwoo.devcordbackend.domain.user.dto;

import java.time.LocalDateTime;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 22.
 */
public record ProfileContextResponse(
        String email,
        String nickname,
        String profileUrl,
        LocalDateTime createdAt,
        MutualFriendSection mutualFriends,
        CommonServerSection commonServers
) {
}
