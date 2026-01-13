package com.junwoo.devcordbackend.domain.room.entity;

import com.junwoo.devcordbackend.config.entity.BaseEntity;
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
 * @date 26. 1. 13.
 */
@Getter
@Entity
@Builder
@Table(name = "channels")
@NoArgsConstructor
@AllArgsConstructor
public class ChannelEntity extends BaseEntity {

    private Long serverId;

    private String name;

    @Enumerated(EnumType.STRING)
    private ChannelType type;

}
