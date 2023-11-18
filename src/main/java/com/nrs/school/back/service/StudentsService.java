package com.nrs.school.back.service;

import com.nrs.school.back.entities.Students;
import com.nrs.school.back.entities.dto.StudentsDTO;

import java.util.List;

public interface StudentsService {
    Students findById(Integer id);

    List<Students> findAll();

    Students create(StudentsDTO studentsDTO);

    Students update(StudentsDTO studentsDTO);

    void delete(Integer studentId);
}

