package com.junwoo.devcordbackend.domain.user.dto;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 22.
 */
public record CommonServerDto(
        Long serverId,
        String name,
        String iconUrl
) {
}
