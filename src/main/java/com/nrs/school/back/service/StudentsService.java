package com.nrs.school.back.service;

import com.nrs.school.back.entities.Students;
import com.nrs.school.back.entities.dto.StudentsDTO;

import java.util.List;
import java.util.Optional;

public interface StudentsService {
    StudentsDTO findById(Integer id);

    List<StudentsDTO> findAll();

    Students create(StudentsDTO studentsDTO);

    Students update(StudentsDTO studentsDTO);

    void delete(Integer studentId);
}

