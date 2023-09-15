package com.ecquaria.restaurantpicker.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecquaria.restaurantpicker.enums.RpSessionStatusEnum;
import com.ecquaria.restaurantpicker.model.RpSession;

@Repository
public interface RpSessionRepository extends JpaRepository<RpSession, Long> {

	Optional<RpSession> findBySessionUuid(UUID sessionUuid);

	long countBySessionCodeAndStatus(String sessionCode, RpSessionStatusEnum status);

	Optional<RpSession> findBySessionCodeAndStatus(String sessionCode, RpSessionStatusEnum status);

}