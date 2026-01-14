package com.junwoo.devcordbackend.domain.room.repository;

import com.junwoo.devcordbackend.domain.room.entity.ServerMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 13.
 */
public interface ServerMemberRepository extends JpaRepository<ServerMemberEntity, Long> {

    Optional<ServerMemberEntity> findByServerIdAndUserId(Long serverId, Long userId);

    boolean existsByServerIdAndUserId(Long serverId, Long userId);

    List<ServerMemberEntity> findByServerId(Long serverId);
}
