package com.nrs.school.back.service;

import com.nrs.school.back.entities.Classroom;
import com.nrs.school.back.entities.dto.ClassroomDTO;
import com.nrs.school.back.exceptions.DataIntegrityViolationException;
import com.nrs.school.back.exceptions.ObjectNotFoundException;
import com.nrs.school.back.repository.ClassroomRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class ClassroomService {

    private static final String JSON_INVALID_MESSAGE = "JSON invalid: ";
    private static final String EXISTING_CLASSROOM_MESSAGE = "Classroom with name %s already exist";
    private static final String CLASSROOM_NOT_FOUND_MESSAGE = "Classroom with id %s not found";

    private final ClassroomRepository repository;

    private final ModelMapper mapper;

    public ClassroomService(ClassroomRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ClassroomDTO> findAll() {
        return repository.findAll().stream().map(c -> mapper.map(c, ClassroomDTO.class)).toList();
    }

    public ClassroomDTO create(ClassroomDTO classroomDTO) {
        String messageValidator = entityValidator(classroomDTO);
        if(!messageValidator.isEmpty()) throw new DataIntegrityViolationException(JSON_INVALID_MESSAGE + messageValidator);

        Optional<Classroom> classroom = findClassroomByClassroomName(classroomDTO.getClassroomName());

        if(classroom.isPresent()) throw new DataIntegrityViolationException(EXISTING_CLASSROOM_MESSAGE.formatted(classroom.get().getClassroomName()));

        var classroomEntity = mapper.map(classroomDTO, Classroom.class);

        return mapper.map(repository.save(classroomEntity), ClassroomDTO.class);
    }

    public Classroom findById(Long classroomId) {
        return repository.findById(classroomId).orElseThrow(() -> new ObjectNotFoundException(CLASSROOM_NOT_FOUND_MESSAGE.formatted(classroomId)));
    }

    private String entityValidator(ClassroomDTO classroomDTO){
        String message = "";
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<ClassroomDTO>> violations = validator.validate(classroomDTO);
        if(!violations.isEmpty()){
            for(ConstraintViolation<ClassroomDTO> violation: violations) message += violation.getMessage() + "; ";
        }
        return message;

    }

    public ClassroomDTO findByClassroomId(String classroomId) {
        var classroom = repository.findByClassroomId(classroomId);
        if (classroom.isEmpty()) {
            throw new ObjectNotFoundException(CLASSROOM_NOT_FOUND_MESSAGE.formatted(classroomId));
        }
        return mapper.map(classroom, ClassroomDTO.class);
    }

    public Optional<Classroom> findClassroomByClassroomName(String classroomName){
        return repository.findByClassroomName(classroomName);
    }
}
