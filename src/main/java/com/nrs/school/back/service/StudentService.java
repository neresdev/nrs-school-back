package com.nrs.school.back.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.nrs.school.back.entities.Student;
import com.nrs.school.back.entities.dto.StudentDTO;
import com.nrs.school.back.exceptions.DataIntegratyViolationException;
import com.nrs.school.back.exceptions.ObjectNotFoundException;
import com.nrs.school.back.repository.StudentRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@Service
public class StudentService{

    private static final String NOT_FOUND_MESSAGE = "Student with registration %s not found";
    private static final String JSON_INVALID_MESSAGE = "JSON invalid: ";
    private static final String EXISTING_STUDENT_MESSAGE = "Student with registration %s already exists";

    private final StudentRepository repository;

    private final ModelMapper mapper;

    public StudentService(StudentRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<StudentDTO> findAll() {
        return repository.findAll().stream().map(u -> mapper.map(u, StudentDTO.class)).collect(Collectors.toList());
    }

    public StudentDTO findByRegistration(String registration) {
        return mapper.map(repository.findByRegistration(registration).orElseThrow(() -> new ObjectNotFoundException(NOT_FOUND_MESSAGE.formatted(registration))), StudentDTO.class);
    }

    public StudentDTO create(StudentDTO studentsDTO) {
        String messageValidator = entityValidator(studentsDTO);
        if(!messageValidator.isEmpty()) throw new DataIntegratyViolationException(JSON_INVALID_MESSAGE + messageValidator);
        Optional<Student> student = repository.findByRegistration(studentsDTO.getRegistration());
        if(student.isPresent()) throw new DataIntegratyViolationException(String.format(EXISTING_STUDENT_MESSAGE, student.get().getRegistration()));
        return mapper.map(repository.save(mapper.map(studentsDTO, Student.class)), StudentDTO.class);
    }

    public StudentDTO update(StudentDTO studentsDTO) {
        String messageValidator = entityValidator(studentsDTO);
        if(!messageValidator.isEmpty()) throw new DataIntegratyViolationException(JSON_INVALID_MESSAGE + messageValidator);
        studentsDTO.setStudentId(findByRegistration(studentsDTO.getRegistration()).getStudentId());
        return mapper.map(repository.save(mapper.map(studentsDTO, Student.class)), StudentDTO.class);
    }

    public void delete(String registration) {
        repository.deleteById(findByRegistration(registration).getStudentId());
    }

    private String entityValidator(StudentDTO studentsDTO){
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
