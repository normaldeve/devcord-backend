package com.junwoo.devcordbackend.domain.message.repository;

import com.junwoo.devcordbackend.domain.message.entity.ChannelMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 15.
 */
public interface ChannelMessageRepository extends JpaRepository<ChannelMessageEntity, Long> {
}
