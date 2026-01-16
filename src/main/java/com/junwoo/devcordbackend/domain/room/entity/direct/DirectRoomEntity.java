package com.junwoo.devcordbackend.domain.room.entity.direct;

import com.junwoo.devcordbackend.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 1대1 개인 메시지 소통방
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 13.
 */
@Getter
@Builder
@Entity
@Table(name = "direct_rooms")
@NoArgsConstructor
public class DirectRoomEntity extends BaseEntity {

}
