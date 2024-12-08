package com.nrs.school.back.service.impl;

import com.nrs.school.back.entities.Student;
import com.nrs.school.back.entities.dto.StudentDTO;
import com.nrs.school.back.exceptions.DataIntegratyViolationException;
import com.nrs.school.back.exceptions.ObjectNotFoundException;
import com.nrs.school.back.repository.StudentRepository;
import com.nrs.school.back.service.StudentService;

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private static final String NOT_FOUND_MESSAGE = "Student with registration %s not found";
    private static final String JSON_INVALID_MESSAGE = "JSON invalid: ";
    private static final String EXISTING_STUDENT_MESSAGE = "Student with registration %s already exists";

    private final StudentRepository repository;

    private final ModelMapper mapper;

    public StudentServiceImpl(StudentRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<StudentDTO> findAll() {
        return repository.findAll().stream().map(u -> mapper.map(u, StudentDTO.class)).collect(Collectors.toList());
    }

    @Override
    public StudentDTO findByRegistration(String registration) {
        return mapper.map(repository.findByRegistration(registration).orElseThrow(() -> new ObjectNotFoundException(NOT_FOUND_MESSAGE.formatted(registration))), StudentDTO.class);
    }

    @Override
    public StudentDTO create(StudentDTO studentsDTO) {
        String messageValidator = entityValidator(studentsDTO);
        if(!messageValidator.isEmpty()) throw new DataIntegratyViolationException(JSON_INVALID_MESSAGE + messageValidator);
        Optional<Student> student = repository.findByRegistration(studentsDTO.getRegistration());
        if(student.isPresent()) throw new DataIntegratyViolationException(String.format(EXISTING_STUDENT_MESSAGE, student.get().getRegistration()));
        return mapper.map(repository.save(mapper.map(studentsDTO, Student.class)), StudentDTO.class);
    }

    @Override
    public StudentDTO update(StudentDTO studentsDTO) {
        String messageValidator = entityValidator(studentsDTO);
        if(!messageValidator.isEmpty()) throw new DataIntegratyViolationException(JSON_INVALID_MESSAGE + messageValidator);
        studentsDTO.setStudentId(findByRegistration(studentsDTO.getRegistration()).getStudentId());
        return mapper.map(repository.save(mapper.map(studentsDTO, Student.class)), StudentDTO.class);
    }

    @Override
    public void delete(String registration) {
        repository.deleteById(findByRegistration(registration).getStudentId());
    }
}
