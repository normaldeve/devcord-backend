package com.junwoo.devcordbackend.domain.room.repository.direct;

import com.junwoo.devcordbackend.domain.room.entity.direct.DirectRoomMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 15.
 */
public interface DirectRoomMemberRepository extends JpaRepository<DirectRoomMemberEntity, Long> {

    boolean existsByDirectRoomIdAndUserId(Long directRoomId, Long userId);

}
