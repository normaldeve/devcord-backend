package com.junwoo.devcordbackend.domain.auth.controller;

import com.junwoo.devcordbackend.config.exception.ErrorCode;
import com.junwoo.devcordbackend.domain.auth.dto.TokenResponse;
import com.junwoo.devcordbackend.domain.auth.exception.AuthException;
import com.junwoo.devcordbackend.domain.auth.service.AuthService;
import com.junwoo.devcordbackend.domain.auth.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 12.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/refresh")
    public ResponseEntity<String> refresh(
            @RequestHeader("Authorization") String authorization
    ) {

        String refreshToken = extractToken(authorization);
        String newAccessToken = authService.refreshAccessToken(refreshToken);

        return ResponseEntity.ok(newAccessToken);
    }

    private String extractToken(String header) {
        if (!StringUtils.hasText(header) || !header.startsWith("Bearer ")) {
            throw new AuthException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        return header.substring(7);
    }
}
