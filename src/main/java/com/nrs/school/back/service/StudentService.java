package com.nrs.school.back.service;

import com.nrs.school.back.entities.dto.StudentDTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.List;
import java.util.Set;

public interface StudentService {
    StudentDTO findByRegistration(String registration);

    List<StudentDTO> findAll();

    StudentDTO create(StudentDTO studentsDTO);

    StudentDTO update(StudentDTO studentsDTO);

    void delete(String registration);

    default String entityValidator(StudentDTO studentsDTO){
        String message = "";
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<StudentDTO>> violations = validator.validate(studentsDTO);
        if(!violations.isEmpty()){
            for(ConstraintViolation<StudentDTO> violation: violations) message += violation.getMessage() + "; ";
        }
        return message;

    }
}

