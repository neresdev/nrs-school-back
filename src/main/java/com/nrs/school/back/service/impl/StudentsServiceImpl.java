package com.nrs.school.back.service.impl;

import com.nrs.school.back.entities.Students;
import com.nrs.school.back.entities.dto.StudentsDTO;
import com.nrs.school.back.exceptions.DataIntegratyViolationException;
import com.nrs.school.back.exceptions.ObjectNotFoundException;
import com.nrs.school.back.repository.StudentsRepository;
import com.nrs.school.back.service.StudentsService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentsServiceImpl implements StudentsService {

    public static final String NOT_FOUND_MESSAGE = "Student with registration %s not found";
    public static final String JSON_INVALID_MESSAGE = "JSON invalid: ";
    public static final String EXISTING_STUDENT_MESSAGE = "Student with registration %s already exists";

    @Autowired
    private StudentsRepository repository; // TODO -> add @RequireArgsConstructor annotation

    @Autowired
    private ModelMapper mapper; // TODO -> add @RequireArgsConstructor annotation
    
    @Override
    public List<StudentsDTO> findAll() {
        return repository.findAll().stream().map(u -> mapper.map(u, StudentsDTO.class)).collect(Collectors.toList());
    }

    @Override
    public StudentsDTO findByRegistration(String registration) {
        return mapper.map(repository.findByRegistration(registration).orElseThrow(() -> new ObjectNotFoundException(String.format(NOT_FOUND_MESSAGE, registration))), StudentsDTO.class);
    }

    @Override
    public StudentsDTO create(StudentsDTO studentsDTO) {
        String messageValidator = entityValidator(studentsDTO);
        Optional<Students> student = repository.findByRegistration(studentsDTO.getRegistration());
        if(!messageValidator.isEmpty()) throw new DataIntegratyViolationException(JSON_INVALID_MESSAGE + messageValidator);
        if(student.isPresent()) throw new DataIntegratyViolationException(EXISTING_STUDENT_MESSAGE + student.get().getRegistration());
        return mapper.map(repository.save(mapper.map(studentsDTO, Students.class)), StudentsDTO.class);
    }

    @Override
    public StudentsDTO update(StudentsDTO studentsDTO) {
        studentsDTO.setId(findByRegistration(studentsDTO.getRegistration()).getId());
        return mapper.map(repository.save(mapper.map(studentsDTO, Students.class)), StudentsDTO.class);
    }

    @Override
    public void delete(String registration) {
        repository.deleteById(findByRegistration(registration).getId());
    }
}
