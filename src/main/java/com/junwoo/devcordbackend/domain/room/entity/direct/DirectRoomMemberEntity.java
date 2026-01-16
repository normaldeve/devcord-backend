package com.junwoo.devcordbackend.domain.room.entity.direct;

import com.junwoo.devcordbackend.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 15.
 */
@Getter
@Builder
@Entity
@Table(name = "direct_room_members")
@NoArgsConstructor
@AllArgsConstructor
public class DirectRoomMemberEntity extends BaseEntity {

    private Long directRoomId;

    private Long userId;

    public static DirectRoomMemberEntity createMember(Long directRoomId, Long userId) {
        return DirectRoomMemberEntity.builder()
                .directRoomId(directRoomId)
                .userId(userId)
                .build();
    }
}
