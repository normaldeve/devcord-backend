package com.junwoo.devcordbackend.domain.room.entity;

import com.junwoo.devcordbackend.config.entity.BaseEntity;
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
}
