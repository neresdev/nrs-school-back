package com.nrs.school.back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nrs.school.back.entities.StudentRecoveryEntity;

@Repository
public interface StudentRecoveryRepository extends JpaRepository<StudentRecoveryEntity, Long> {

    List<StudentRecoveryEntity> findByStudentId(Long studentId);
}
