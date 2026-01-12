package com.junwoo.devcordbackend.domain.auth.security;

import com.junwoo.devcordbackend.domain.auth.dto.UserDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 12.
 */
@Getter
@RequiredArgsConstructor
public class DevcordUserDetails implements UserDetails {

    private final UserDTO userDTO;
    private final String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_".concat(userDTO.role().name())));
    }

    @Override
    public String getUsername() {
        return userDTO.email();
    }
}
