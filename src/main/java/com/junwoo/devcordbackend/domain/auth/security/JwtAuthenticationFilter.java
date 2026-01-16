package com.junwoo.devcordbackend.domain.auth.security;

import com.junwoo.devcordbackend.domain.auth.dto.AuthDTO;
import com.junwoo.devcordbackend.domain.auth.dto.SecurityPassUrlList;
import com.junwoo.devcordbackend.domain.auth.jwt.JwtTokenProvider;
import com.junwoo.devcordbackend.domain.auth.security.userdetail.DevcordUserDetails;
import com.junwoo.devcordbackend.domain.user.entity.Role;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 12.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtTokenProvider jwtTokenProvider;
    private final PathMatcher pathMatcher;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = resolveToken(request);

        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
            Long userId = jwtTokenProvider.getClaims(token).get("id", Long.class);
            String email = jwtTokenProvider.getEmailFromToken(token);
            String role = jwtTokenProvider.getClaims(token).get("role", String.class);

            AuthDTO user = AuthDTO.builder()
                    .id(userId)
                    .email(email)
                    .role(Role.valueOf(role))
                    .build();

            DevcordUserDetails userDetails = new DevcordUserDetails(user, null);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, List.of(new SimpleGrantedAuthority("ROLE_" + role)));

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            log.info("JWT 인증 성공 - email: {}", email);
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return Arrays.stream(SecurityPassUrlList.ALL)
                .anyMatch(pattern -> pathMatcher.match(pattern, path));
    }
}
