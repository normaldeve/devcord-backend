package com.junwoo.devcordbackend.domain.room.service;

import com.junwoo.devcordbackend.config.exception.ErrorCode;
import com.junwoo.devcordbackend.domain.room.dto.ServerMemberResponse;
import com.junwoo.devcordbackend.domain.room.exception.ServerException;
import com.junwoo.devcordbackend.domain.room.repository.ServerMemberRepository;
import com.junwoo.devcordbackend.domain.user.entity.UserEntity;
import com.junwoo.devcordbackend.domain.user.exception.UserException;
import com.junwoo.devcordbackend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 13.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ServerMemberService {

    private final ServerMemberRepository serverMemberRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<ServerMemberResponse> getServerMembers(Long severId, Long requesterId) {
        if (!serverMemberRepository.existsByServerIdAndUserId(severId, requesterId)) {
            throw new ServerException(ErrorCode.NOT_A_SERVER_MEMBER);
        }

        return serverMemberRepository.findByServerId(severId).stream()
                .map(member -> {
                    UserEntity user = userRepository.findById(member.getUserId())
                            .orElseThrow(() -> new UserException(ErrorCode.CANNOT_FOUND_USER));

                    return ServerMemberResponse.from(member, user.getNickname(), false);
                })
                .toList();
    }
}
