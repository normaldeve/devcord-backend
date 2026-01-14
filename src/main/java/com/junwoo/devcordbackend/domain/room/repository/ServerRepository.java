package com.junwoo.devcordbackend.domain.room.repository;

import com.junwoo.devcordbackend.domain.room.dto.ServerResponse;
import com.junwoo.devcordbackend.domain.room.entity.ServerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 13.
 */
public interface ServerRepository extends JpaRepository<ServerEntity, Long> {

    @Query("""
        select new com.junwoo.devcordbackend.domain.room.dto.ServerResponse(
            s.id,
            s.name,
            s.iconUrl
        )
        from ServerEntity s
        join ServerMemberEntity sm
            on sm.serverId = s.id
        where sm.userId = :userId
    """)
    List<ServerResponse> findMyServers(Long userId);

}
