package com.nrs.school.back.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nrs.school.back.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
}
