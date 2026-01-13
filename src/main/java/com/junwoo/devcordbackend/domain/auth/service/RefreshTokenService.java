package com.junwoo.devcordbackend.domain.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 12.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RedisTemplate<String, String> redisTemplate;

    private static final String PREFIX = "refresh: ";

    public void save(String email, String refreshToken, long ttlMillis) {
        redisTemplate.opsForValue().set(
                PREFIX + email,
                refreshToken,
                ttlMillis,
                TimeUnit.MILLISECONDS
        );

        log.info("[RefreshTokenService] Refresh Token 저장 - email={},", email);
    }

    public String get(String email) {
        String token = redisTemplate.opsForValue().get(PREFIX + email);

        if (token != null) {
            log.info("[RefreshTokenService] Refresh Token 조회 성공 - email={}", email);
        } else {
            log.info("[RefreshTokenService] Refresh Token 조회 실패 - email={}", email);
        }

        return token;
    }

    public void delete(String email) {
        Boolean deleted = redisTemplate.delete(PREFIX + email);

        if (Boolean.TRUE.equals(deleted)) {
            log.info("[RefreshTokenService] Refresh Token 삭제 - email={}", email);
        } else {
            log.info("[RefreshTokenService] Refresh Token 삭제 요청 (존재하지 않음) - email={}", email);
        }
    }
}
