package org.group6.travel.domain.token.repository;

import java.util.Optional;
import org.group6.travel.domain.token.model.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByUserId(Long userId);
    Optional<RefreshTokenEntity> findByRefreshToken(String refreshToken);
}
