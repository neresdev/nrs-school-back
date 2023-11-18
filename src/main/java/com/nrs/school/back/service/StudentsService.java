package com.nrs.school.back.service;

import com.nrs.school.back.entities.Students;
import com.nrs.school.back.entities.dto.StudentDTO;

import java.util.List;

public interface StudentService {
    Students findById(Integer id);

    List<Students> findAll();

    Students create(StudentDTO studentDTO);

    Students update(StudentDTO studentDTO);

    void delete(Integer studentId);
}

