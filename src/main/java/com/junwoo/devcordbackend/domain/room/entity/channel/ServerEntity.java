package com.junwoo.devcordbackend.domain.room.entity.channel;

import com.junwoo.devcordbackend.common.entity.BaseEntity;
import com.junwoo.devcordbackend.domain.room.dto.CreateServerRequest;
import jakarta.persistence.Entity;
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
@Builder
@Entity
@Table(name = "servers")
@NoArgsConstructor
@AllArgsConstructor
public class ServerEntity extends BaseEntity {

    private String name;

    private String iconUrl;

    public static ServerEntity createServer(CreateServerRequest request, String iconUrl) {
        return ServerEntity.builder()
                .name(request.serverName())
                .iconUrl(iconUrl)
                .build();
    }
}