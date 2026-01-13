package com.junwoo.devcordbackend.domain.room.entity;

import com.junwoo.devcordbackend.config.entity.BaseEntity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
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
@Table(name = "direct_rooms")
@NoArgsConstructor
@AllArgsConstructor
public class DirectRoom extends BaseEntity {

}
