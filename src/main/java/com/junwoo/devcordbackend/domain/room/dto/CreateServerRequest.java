package com.junwoo.devcordbackend.domain.room.dto;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 13.
 */
public record CreateServerRequest(
        String serverName,
        String iconUrl
) {
}
