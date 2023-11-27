package com.nrs.school.back.repository;

import com.nrs.school.back.entities.Students;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentsRepository extends JpaRepository<Students, Integer> {
    Optional<Students> findByRegistration(String registration);
    void deleteByRegistration(String registration);

}
