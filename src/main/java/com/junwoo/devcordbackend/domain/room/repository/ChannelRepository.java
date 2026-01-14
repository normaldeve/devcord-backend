package com.junwoo.devcordbackend.domain.room.repository;

import com.junwoo.devcordbackend.domain.room.entity.ChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 13.
 */
public interface ChannelRepository extends JpaRepository<ChannelEntity, Long> {

    boolean existsByServerIdAndName(Long serverId, String channelName);

    List<ChannelEntity> findByServerIdOrderByCreatedAtAsc(Long serverId);
}
