package com.nrs.school.back.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nrs.school.back.entities.ClassroomEntity;

@Repository
public interface ClassroomRepository extends JpaRepository<ClassroomEntity, Long> {

    Optional<ClassroomEntity> findByClassroomName(String classroomName);

    Optional<ClassroomEntity> findByClassroomReferenceCode(UUID classroomId);

}
