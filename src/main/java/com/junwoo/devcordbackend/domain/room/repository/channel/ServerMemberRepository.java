package com.junwoo.devcordbackend.domain.room.repository.channel;

import com.junwoo.devcordbackend.domain.room.entity.channel.ServerMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 13.
 */
public interface ServerMemberRepository extends JpaRepository<ServerMemberEntity, Long> {

    Optional<ServerMemberEntity> findByUserId(Long userId);

    Optional<ServerMemberEntity> findByServerIdAndUserId(Long serverId, Long userId);

    boolean existsByServerIdAndUserId(Long serverId, Long userId);

    List<ServerMemberEntity> findByServerId(Long serverId);

    @Query("""
    SELECT sm
    FROM ServerMemberEntity sm
    WHERE sm.serverId IN :serverIds
    AND sm.userId IN (:userA, :userB)
    """)
    List<ServerMemberEntity> findMembersInServers(
            @Param("serverIds") Set<Long> serverIds,
            @Param("userA") Long userA,
            @Param("userB") Long userB
    );
}
