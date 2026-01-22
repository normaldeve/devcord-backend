package com.junwoo.devcordbackend.domain.user.dto;

import java.util.List;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 22.
 */
public record MutualFriendSection(
        int count,
        List<MutualFriendDto> friends
) {
}
