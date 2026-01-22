package com.junwoo.devcordbackend.domain.friend.service;

import com.junwoo.devcordbackend.common.exception.ErrorCode;
import com.junwoo.devcordbackend.domain.notification.dto.event.FriendRequestAcceptedEvent;
import com.junwoo.devcordbackend.domain.notification.dto.event.FriendRequestSentEvent;
import com.junwoo.devcordbackend.domain.user.dto.FriendRequestResponse;
import com.junwoo.devcordbackend.domain.user.dto.FriendResponse;
import com.junwoo.devcordbackend.domain.friend.entity.FriendEntity;
import com.junwoo.devcordbackend.domain.friend.entity.FriendStatus;
import com.junwoo.devcordbackend.domain.user.entity.UserEntity;
import com.junwoo.devcordbackend.domain.friend.exception.FriendException;
import com.junwoo.devcordbackend.domain.friend.repository.FriendRepository;
import com.junwoo.devcordbackend.domain.user.repository.UserRepository;
import com.junwoo.devcordbackend.domain.user.service.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 14.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FriendService {

    private final UserValidator userValidator;
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;

    public void sendFriendRequest(Long requesterId, Long receiverId) {

        if (requesterId.equals(receiverId)) {
            throw new FriendException(ErrorCode.CANNOT_REQUEST_SELF);
        }

        userValidator.validateUserExists(receiverId);

        friendRepository.findByRequesterIdAndReceiverIdOrRequesterIdAndReceiverId(requesterId, receiverId, receiverId, requesterId)
                .ifPresent(friend -> {
                    switch (friend.getStatus()) {
                        case ACCEPTED -> throw new FriendException(ErrorCode.ALREADY_FRIEND);
                        case PENDING -> throw new FriendException(ErrorCode.ALREADY_REQUEST);
                        case BLOCKED -> throw new FriendException(ErrorCode.FRIEND_BLOCKED);
                    }
                });

        FriendEntity request = FriendEntity.request(requesterId, receiverId);
        friendRepository.save(request);

        eventPublisher.publishEvent(new FriendRequestSentEvent(requesterId, receiverId));

        log.info("[FriendService] 친구 요청 전송 - from: {}, to: {}", requesterId, receiverId);

    }

    @Transactional(readOnly = true)
    public List<FriendRequestResponse> getReceivedFriendRequests(Long userId) {

        List<FriendEntity> requests = friendRepository.findByReceiverIdAndStatusOrderByCreatedAtDesc(userId, FriendStatus.PENDING);

        List<Long> requesterIds = requests.stream()
                .map(FriendEntity::getRequesterId)
                .toList();

        Map<Long, UserEntity> userMap = userRepository.findAllById(requesterIds)
                .stream()
                .collect(Collectors.toMap(UserEntity::getId, Function.identity()));

        return requests.stream()
                .map(req -> FriendRequestResponse.from(
                        req, userMap.get(req.getRequesterId())
                )).toList();
    }

    public void acceptFriendRequest(Long receiverId, Long requesterId) {
        FriendEntity request = friendRepository.findByRequesterIdAndReceiverId(requesterId, receiverId)
                .orElseThrow(() -> new FriendException(ErrorCode.CANNOT_FOUND_FRIEND_REQUEST));

        userValidator.checkDuplicateFriendRequest(request.getStatus());

        request.accept();

        FriendEntity reverse = FriendEntity.reverseRequest(receiverId, requesterId);

        friendRepository.save(reverse);

        eventPublisher.publishEvent(new FriendRequestAcceptedEvent(receiverId, requesterId));

        log.info("[FriendService] 친구 요청 수락 - {} <-> {}", requesterId, receiverId);
    }

    public void blockFriendRequest(Long receiverId, Long requesterId) {
        FriendEntity request = friendRepository.findByRequesterIdAndReceiverId(requesterId, receiverId)
                .orElseThrow(() -> new FriendException(ErrorCode.CANNOT_FOUND_FRIEND_REQUEST));

        request.blocked();

        log.info("[FriendService] 친구 요청 차단 - {} <-> {}", requesterId, receiverId);
    }

    @Transactional(readOnly = true)
    public List<FriendResponse> getMyFriends(Long userId, String keyword) {
        List<FriendEntity> friends = friendRepository.findByRequesterIdAndStatus(userId, FriendStatus.ACCEPTED);

        if (friends.isEmpty()) {
            return List.of();
        }

        List<Long> friendIds = friends.stream()
                .map(FriendEntity::getReceiverId)
                .toList();

        List<UserEntity> users;
        if (keyword == null || keyword.isBlank()) {
            users = userRepository.findByIdInOrderByNicknameAsc(friendIds);
        } else {
            users = userRepository.findByIdInAndNicknameContainingIgnoreCaseOrderByNicknameAsc(friendIds, keyword);
        }

        return users.stream()
                .map(user -> FriendResponse.from(user, false))
                .toList();
    }
}
