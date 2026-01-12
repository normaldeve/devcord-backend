package com.junwoo.devcordbackend.domain.auth.dto;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 12.
 */
public record TokenResponse(
        String accessToken,
        String refreshToken,
        AuthDTO user
) {
}
