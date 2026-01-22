package com.junwoo.devcordbackend.domain.user.service;

import com.junwoo.devcordbackend.common.exception.ErrorCode;
import com.junwoo.devcordbackend.domain.auth.dto.AuthDTO;
import com.junwoo.devcordbackend.domain.friend.service.FriendService;
import com.junwoo.devcordbackend.domain.image.ImageDirectory;
import com.junwoo.devcordbackend.domain.image.ImageUploader;
import com.junwoo.devcordbackend.domain.room.entity.channel.ServerEntity;
import com.junwoo.devcordbackend.domain.room.entity.channel.ServerMemberEntity;
import com.junwoo.devcordbackend.domain.room.entity.channel.ServerRole;
import com.junwoo.devcordbackend.domain.room.repository.channel.ServerMemberRepository;
import com.junwoo.devcordbackend.domain.room.repository.channel.ServerRepository;
import com.junwoo.devcordbackend.domain.room.service.ServerService;
import com.junwoo.devcordbackend.domain.user.dto.*;
import com.junwoo.devcordbackend.domain.user.entity.UserEntity;
import com.junwoo.devcordbackend.domain.user.exception.UserException;
import com.junwoo.devcordbackend.domain.user.mapper.UserMapper;
import com.junwoo.devcordbackend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 12.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserValidator userValidator;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageUploader imageUploader;

    public SignupResponse signup(SignupRequest request) {

        userValidator.checkEmailDuplicate(request.email());

        userValidator.checkNicknameDuplicate(request.nickname());

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

    public String updateProfile(Long userId, MultipartFile file) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorCode.CANNOT_FOUND_USER));

        String newImageUrl = imageUploader.upload(file, ImageDirectory.PROFILE);

        if (user.getProfileUrl() != null && !user.getProfileUrl().isEmpty()) {
            try {
                imageUploader.delete(user.getProfileUrl());
            } catch (Exception e) {
                log.error("[UserService] 사용자 프로필 삭제 실패 - url: {} - error: {}", user.getProfileUrl(), e.getMessage(), e);
            }
        }

        user.updateProfile(newImageUrl);

        log.info("[UserService] 프로필 이미지 변경 - userId: {}, imageUrl: {}", userId, newImageUrl);

        return newImageUrl;
    }

    @Transactional(readOnly = true)
    public AuthDTO findByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(ErrorCode.CANNOT_FOUND_USER));

        return userMapper.toAuthDTO(userEntity);
    }

    @Transactional(readOnly = true)
    public List<UserSearchResponse> searchUsers(Long myId, String keyword) {
        return userRepository.searchUsers(myId, keyword).stream()
                .map(userMapper::toUserSearchRespose)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserInfo getUserInfo(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorCode.CANNOT_FOUND_USER));

        return UserInfo.from(user);
    }
}
