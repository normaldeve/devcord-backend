package com.junwoo.devcordbackend.domain.room.entity;

import com.junwoo.devcordbackend.config.entity.BaseEntity;
import com.junwoo.devcordbackend.domain.room.dto.CreateChannelRequest;
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

    public static ChannelEntity createTextChannel(Long serverId) {
        return ChannelEntity.builder()
                .serverId(serverId)
                .name("일반")
                .type(ChannelType.TEXT)
                .build();
    }

    public static ChannelEntity createVoiceChannel(Long serverId) {
        return ChannelEntity.builder()
                .serverId(serverId)
                .name("일반")
                .type(ChannelType.VOICE)
                .build();
    }

    public static ChannelEntity createChannel(Long serverId, CreateChannelRequest request) {
        return ChannelEntity.builder()
                .serverId(serverId)
                .name(request.channelName())
                .type(request.channelType())
                .build();
    }

    public void renameChannel(String newName) {
        this.name = newName;
    }
}
