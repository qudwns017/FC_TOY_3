package org.group6.travel.domain.user.repository;

import org.group6.travel.domain.user.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
