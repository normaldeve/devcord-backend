package com.junwoo.devcordbackend.domain.notification.repository;

import com.junwoo.devcordbackend.domain.notification.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author junnukim1007gmail.com
 * @date 26. 1. 21.
 */
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
}
