package com.junwoo.devcordbackend.domain.user.service;

import com.junwoo.devcordbackend.domain.friend.service.FriendService;
import com.junwoo.devcordbackend.domain.room.entity.channel.ServerEntity;
import com.junwoo.devcordbackend.domain.room.repository.channel.ServerRepository;
import com.junwoo.devcordbackend.domain.room.service.ServerService;
import com.junwoo.devcordbackend.domain.user.dto.*;
import com.junwoo.devcordbackend.domain.user.entity.UserEntity;
import com.junwoo.devcordbackend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 22.
 */
@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final FriendService friendService;
    private final ServerService serverService;
    private final ServerRepository serverRepository;
    private final UserService userService;

    @Transactional(readOnly = true)
    public ProfileContextResponse getProfileContext(Long viewerId, Long targetUserId) {

        UserInfo targetUserInfo = userService.getUserInfo(targetUserId);

        // 1. 공통 친구
        Set<Long> mutualFriendIds = friendService.findMutualFriendIds(viewerId, targetUserId);
        List<UserEntity> mutualFriends = userRepository.findAllById(mutualFriendIds);

        // 2. 공통 서버
        Set<Long> commonServerIds = serverService.findCommonServerIds(viewerId, targetUserId);
        List<ServerEntity> servers = serverRepository.findAllById(commonServerIds);

        return new ProfileContextResponse(
                targetUserInfo.email(),
                targetUserInfo.nickname(),
                targetUserInfo.profileUrl(),
                targetUserInfo.createdAt(),
                new MutualFriendSection(
                        mutualFriendIds.size(),
                        mutualFriends.stream()
                                .map(u -> new MutualFriendDto(u.getId(), u.getNickname(), u.getProfileUrl()))
                                .toList()
                ),
                new CommonServerSection(
                        commonServerIds.size(),
                        servers.stream()
                                .map(s -> new CommonServerDto(
                                        s.getId(),
                                        s.getName(),
                                        s.getIconUrl()
                                ))
                                .toList()
                )
        );
    }
}
