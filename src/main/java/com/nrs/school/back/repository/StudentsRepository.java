package com.nrs.school.back.repository;

import com.nrs.school.back.entities.Students;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentsRepository extends JpaRepository<Students, Integer> {

    @Query("SELECT name from Students")
    List<String> findAllStudents();

    List<Students> findAll();

}
