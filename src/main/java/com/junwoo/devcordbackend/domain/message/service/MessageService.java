package com.junwoo.devcordbackend.domain.message.service;

import com.junwoo.devcordbackend.common.exception.ErrorCode;
import com.junwoo.devcordbackend.domain.message.dto.ChannelMessageResponse;
import com.junwoo.devcordbackend.domain.message.dto.DirectMessageResponse;
import com.junwoo.devcordbackend.domain.message.dto.SendMessageRequest;
import com.junwoo.devcordbackend.domain.message.entity.ChannelMessageEntity;
import com.junwoo.devcordbackend.domain.message.entity.DirectMessageEntity;
import com.junwoo.devcordbackend.domain.message.repository.ChannelMessageRepository;
import com.junwoo.devcordbackend.domain.message.repository.DirectMessageRepository;
import com.junwoo.devcordbackend.domain.user.entity.UserEntity;
import com.junwoo.devcordbackend.domain.user.exception.UserException;
import com.junwoo.devcordbackend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 15.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MessageService {

    private final ChannelMessageRepository channelMessageRepository;
    private final DirectMessageRepository directMessageRepository;
    private final UserRepository userRepository;

    /**
     * 채널 메시지 전송
     */
    public ChannelMessageResponse sendChannelMessage(Long channelId, Long senderId, SendMessageRequest request) {

        ChannelMessageEntity message = ChannelMessageEntity.createMessage(channelId, senderId, request);

        ChannelMessageEntity saved = channelMessageRepository.save(message);

        UserEntity sender = userRepository.findById(senderId)
                .orElseThrow(() -> new UserException(ErrorCode.CANNOT_FOUND_USER));

        log.info("[MessageService] 채널 메시지 전송 - channelId: {}, senderId: {}", channelId, sender);

        return ChannelMessageResponse.from(saved, sender.getNickname(), sender.getProfileUrl());
    }

    /**
     * 개인 메시지 전송
     */
    public DirectMessageResponse sendDirectMessage(Long directRoomId, Long senderId, SendMessageRequest request) {

        DirectMessageEntity message = DirectMessageEntity.createMessage(directRoomId, senderId, request);

        DirectMessageEntity saved = directMessageRepository.save(message);

        UserEntity sender = userRepository.findById(senderId)
                .orElseThrow(() -> new UserException(ErrorCode.CANNOT_FOUND_USER));

        log.info("[MessageService] DM 전송 - directRoomId: {}, senderId: {}", directRoomId, senderId);

        return DirectMessageResponse.from(saved, sender.getNickname(), sender.getProfileUrl());
    }

    @Transactional(readOnly = true)
    public Slice<ChannelMessageResponse> getChannelMessages(Long channelId, Long lastMessageId, int size) {

        Pageable pageable = PageRequest.of(0, size);

        return channelMessageRepository.findChannelMessages(channelId, lastMessageId, pageable);
    }

    @Transactional(readOnly = true)
    public Slice<DirectMessageResponse> getDirectMessages(
            Long roomId,
            Long lastMessageId,
            int size
    ) {
        Pageable pageable = PageRequest.of(0, size);

        return directMessageRepository.findMessages(roomId, lastMessageId, pageable);
    }
}
