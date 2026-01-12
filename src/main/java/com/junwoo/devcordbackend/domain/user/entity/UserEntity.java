package com.junwoo.devcordbackend.domain.user.entity;

import com.junwoo.devcordbackend.config.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 12.
 */
@Getter
@Entity
@Table(name = "user")
@NoArgsConstructor
public class UserEntity extends BaseEntity {

    private String nickname;

    private String email;

    private String password;

    private String profileUrl;

    @Enumerated(EnumType.STRING)
    private Role role;
}
