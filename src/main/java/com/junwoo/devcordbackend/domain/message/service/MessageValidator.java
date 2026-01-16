package com.junwoo.devcordbackend.domain.message.service;

import com.junwoo.devcordbackend.common.exception.ErrorCode;
import com.junwoo.devcordbackend.domain.room.exception.ServerException;
import com.junwoo.devcordbackend.domain.room.repository.channel.ServerMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 15.
 */
@Service
@RequiredArgsConstructor
public class MessageValidator {

    private final ServerMemberRepository serverMemberRepository;

    public void checkServerMember(Long serverId, Long senderId) {
        if (!serverMemberRepository.existsByServerIdAndUserId(serverId, senderId)) {
            throw new ServerException(ErrorCode.NOT_A_SERVER_MEMBER);
        }
    }
}
