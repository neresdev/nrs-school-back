package com.nrs.school.back.service.impl;

import com.nrs.school.back.entities.Students;
import com.nrs.school.back.entities.dto.StudentsDTO;
import com.nrs.school.back.repository.StudentsRepository;
import com.nrs.school.back.service.StudentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentsServiceImpl implements StudentsService {

    @Autowired
    private StudentsRepository repository;

    @Override
    public Students findById(Integer id) {
        return null;
    }

    @Override
    public List<Students> findAll() {
        return repository.findAll();
    }

    @Override
    public Students create(StudentsDTO studentsDTO) {
        return null;
    }

    @Override
    public Students update(StudentsDTO studentsDTO) {
        return null;
    }

    @Override
    public void delete(Integer studentId) {

    }
}
