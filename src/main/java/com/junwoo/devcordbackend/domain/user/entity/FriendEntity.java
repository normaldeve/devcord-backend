package com.junwoo.devcordbackend.domain.user.entity;

import com.junwoo.devcordbackend.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 14.
 */
@Getter
@Builder
@Entity
@Table(name = "friends")
@NoArgsConstructor
@AllArgsConstructor
public class FriendEntity extends BaseEntity {

    private Long requesterId;

    private Long receiverId;

    @Enumerated(EnumType.STRING)
    private FriendStatus status;

    public static FriendEntity request(Long from, Long to) {
        return FriendEntity.builder()
                .requesterId(from)
                .receiverId(to)
                .status(FriendStatus.PENDING)
                .build();
    }

    public void accept() {
        this.status = FriendStatus.ACCEPTED;
    }

    public void blocked() {
        this.status = FriendStatus.BLOCKED;
    }

    public static FriendEntity reverseRequest(Long from, Long to) {
        return FriendEntity.builder()
                .requesterId(from)
                .receiverId(to)
                .status(FriendStatus.ACCEPTED)
                .build();
    }
}
