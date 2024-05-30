package org.group6.travel.domain.user.repository;

import java.util.Optional;
import org.group6.travel.domain.user.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<UserEntity> findFirstByUserIdOrderByUserIdDesc(Long userId);
    Optional<UserEntity> findFirstByEmailAndEncryptedPasswordOrderByUserIdDesc(String email, String encryptedPassword);


}
