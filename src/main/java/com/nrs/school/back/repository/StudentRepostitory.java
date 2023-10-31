package com.nrs.school.back.repository;

import com.nrs.school.back.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepostitory extends JpaRepository<Student, Integer> {
    @Query("SELECT name FROM Student")
    List<String> findAllStudents();
}
