package com.nrs.school.back.repository;

import com.nrs.school.back.entities.ClassroomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClassroomRepository extends JpaRepository<ClassroomEntity, Long> {

    Optional<ClassroomEntity> findByClassroomName(String classroomName);

    Optional<ClassroomEntity> findByClassroomReferenceCode(UUID classroomId);

}
