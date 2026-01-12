package com.junwoo.devcordbackend.domain.auth.dto;

import com.junwoo.devcordbackend.domain.user.entity.Role;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 12.
 */
public record UserDTO(
        Long id,
        String email,
        String profileUrl,
        Boolean online,
        Role role
) {
}
