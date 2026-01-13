package com.junwoo.devcordbackend.domain.user.service;

import com.junwoo.devcordbackend.config.exception.ErrorCode;
import com.junwoo.devcordbackend.domain.auth.dto.AuthDTO;
import com.junwoo.devcordbackend.domain.user.dto.SignupRequest;
import com.junwoo.devcordbackend.domain.user.dto.SignupResponse;
import com.junwoo.devcordbackend.domain.user.entity.UserEntity;
import com.junwoo.devcordbackend.domain.user.exception.UserException;
import com.junwoo.devcordbackend.domain.user.mapper.UserMapper;
import com.junwoo.devcordbackend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 12.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserValidator userValidator;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignupResponse signup(SignupRequest request) {

        if (userValidator.checkEmailDuplicate(request.email())) {
            log.error("[Userservice] 회원 가입 실패 - 이미 존재하는 이메일: {}", request.email());

            throw new UserException(ErrorCode.ALREADY_EXISTS_EMAIL);
        }

        UserEntity user = UserEntity.createUser(request, passwordEncoder);

        UserEntity savedUser = userRepository.save(user);

        log.info("[UserService] 회원 가입 성공 - email: {}, nickname: {}", savedUser.getEmail(), savedUser.getNickname());

        return new SignupResponse(
                savedUser.getId(),
                savedUser.getNickname(),
                savedUser.getEmail(),
                savedUser.getProfileUrl(),
                savedUser.getRole(),
                savedUser.getCreatedAt()
        );
    }

    @Transactional(readOnly = true)
    public AuthDTO findByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(ErrorCode.CANNOT_FOUND_USER));

        return userMapper.toAuthDTO(userEntity);
    }
}
