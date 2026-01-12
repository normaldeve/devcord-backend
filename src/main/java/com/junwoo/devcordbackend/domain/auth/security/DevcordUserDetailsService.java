package com.junwoo.devcordbackend.domain.auth.security;

import com.junwoo.devcordbackend.domain.auth.dto.AuthDTO;
import com.junwoo.devcordbackend.domain.user.entity.UserEntity;
import com.junwoo.devcordbackend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 12.
 */
@Service
@RequiredArgsConstructor
public class DevcordUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("입력하신 이메일로 사용자를 찾을 수 없습니다: " + email));

        AuthDTO userDTO = new AuthDTO(
                user.getId(),
                user.getEmail(),
                user.getProfileUrl(),
                false,
                user.getRole()
        );

        return new DevcordUserDetails(userDTO, user.getPassword());
    }
}
