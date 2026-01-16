package com.junwoo.devcordbackend.domain.room.service;

import com.junwoo.devcordbackend.domain.room.dto.DirectRoomListResponse;
import com.junwoo.devcordbackend.domain.room.entity.direct.DirectRoomEntity;
import com.junwoo.devcordbackend.domain.room.entity.direct.DirectRoomMemberEntity;
import com.junwoo.devcordbackend.domain.room.repository.direct.DirectRoomMemberRepository;
import com.junwoo.devcordbackend.domain.room.repository.direct.DirectRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 15.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DirectRoomService {

    private final DirectRoomRepository directRoomRepository;
    private final DirectRoomMemberRepository directRoomMemberRepository;

    public Long createDirectRoom(Long userId1, Long userId2) {

        return directRoomRepository.findByTwoUsers(userId1, userId2)
                .map(DirectRoomEntity::getId)
                .orElseGet(() -> {
                    DirectRoomEntity room = directRoomRepository.save(new DirectRoomEntity());

                    DirectRoomMemberEntity member1 = DirectRoomMemberEntity.createMember(room.getId(), userId1);
                    DirectRoomMemberEntity member2 = DirectRoomMemberEntity.createMember(room.getId(), userId2);

                    directRoomMemberRepository.saveAll(List.of(member1, member2));

                    log.info("[MessageService] DM 방 생성 - roomId: {}, users : {} <-> {}", room.getId(), userId1, userId2);

                    return room.getId();
                });
    }

    @Transactional(readOnly = true)
    public List<DirectRoomListResponse> getMyDirectRooms(Long myId) {

        return directRoomRepository.findMyDirectRooms(myId);
    }
}
