package com.nrs.school.back.service;

import com.nrs.school.back.entities.dto.StudentsDTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.List;
import java.util.Set;

public interface StudentsService {
    StudentsDTO findByRegistration(String registration);

    List<StudentsDTO> findAll();

    StudentsDTO create(StudentsDTO studentsDTO);

    StudentsDTO update(StudentsDTO studentsDTO);

    void delete(String registration);

    default String entityValidator(StudentsDTO studentsDTO){
        String message = "";
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<StudentsDTO>> violations = validator.validate(studentsDTO);
        if(!violations.isEmpty()){
            for(ConstraintViolation<StudentsDTO> violation: violations) message += violation.getMessage() + "; ";
        }
        return message;

    }
}

