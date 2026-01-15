package com.junwoo.devcordbackend.domain.auth.dto;

import com.junwoo.devcordbackend.domain.user.entity.Role;
import lombok.Builder;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 12.
 */
@Builder
public record AuthDTO(
        Long id,
        String email,
        String nickname,
        String profileUrl,
        Boolean online,
        Role role
) {
}
