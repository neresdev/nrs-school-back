package com.nrs.school.back.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.nrs.school.back.entities.StudentRecoveryEntity;
import com.nrs.school.back.repository.StudentRecoveryRepository;

@Component
public class StudentRecoveryService {

    private final StudentRecoveryRepository repository;

    public StudentRecoveryService(StudentRecoveryRepository repository) {
        this.repository = repository;
    }

    public List<StudentRecoveryEntity> findAll() {
        return repository.findAll();
    }
}
