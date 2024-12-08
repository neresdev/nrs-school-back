package com.nrs.school.back.repository;

import com.nrs.school.back.entities.Student;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findByRegistration(String registration);
    void deleteByRegistration(String registration);

}
