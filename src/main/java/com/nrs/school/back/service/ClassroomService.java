package com.nrs.school.back.service;

import com.nrs.school.back.entities.ClassroomEntity;
import com.nrs.school.back.entities.dto.classroom.ClassroomDataResponse;
import com.nrs.school.back.entities.dto.classroom.ClassroomResponse;
import com.nrs.school.back.exceptions.DataIntegrityViolationException;
import com.nrs.school.back.exceptions.ObjectNotFoundException;
import com.nrs.school.back.repository.ClassroomRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.*;

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

    public ClassroomResponse findAll() {
        final var entities = repository.findAll();
        final var classrooms = entities.stream().map(c -> mapper.map(c, ClassroomDataResponse.class)).toList();
        return new ClassroomResponse(classrooms);
    }

    public ClassroomDataResponse create(ClassroomDataResponse classroomDataResponse) {
        String messageValidator = entityValidator(classroomDataResponse);
        if(!messageValidator.isEmpty()) throw new DataIntegrityViolationException(JSON_INVALID_MESSAGE + messageValidator);

        Optional<ClassroomEntity> classroom = findClassroomByClassroomName(classroomDataResponse.getClassroomName());

        if(classroom.isPresent()) throw new DataIntegrityViolationException(EXISTING_CLASSROOM_MESSAGE.formatted(classroom.get().getClassroomName()));

        var classroomEntity = mapper.map(classroomDataResponse, ClassroomEntity.class);

        return mapper.map(repository.save(classroomEntity), ClassroomDataResponse.class);
    }

    public ClassroomEntity findById(Long classroomId) {
        return repository.findById(classroomId).orElseThrow(() -> new ObjectNotFoundException(CLASSROOM_NOT_FOUND_MESSAGE.formatted(classroomId)));
    }

    private String entityValidator(ClassroomDataResponse classroomDataResponse){
        String message = "";
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<ClassroomDataResponse>> violations = validator.validate(classroomDataResponse);
        if(!violations.isEmpty()){
            for(ConstraintViolation<ClassroomDataResponse> violation: violations) message += violation.getMessage() + "; ";
        }
        return message;

    }

    public String findClassroomNameById(final Long classroomId) {
        final var classroom = findById(classroomId);
        return classroom.getClassroomName();
    }

    public ClassroomDataResponse findByClassroomReferenceCode(UUID classroomReferenceCode) {
        var classroom = repository.findByClassroomReferenceCode(classroomReferenceCode);
        if (classroom.isEmpty()) {
            throw new ObjectNotFoundException(CLASSROOM_NOT_FOUND_MESSAGE.formatted(classroomReferenceCode));
        }
        return mapper.map(classroom, ClassroomDataResponse.class);
    }

    public Optional<ClassroomEntity> findClassroomByClassroomName(String classroomName){
        return repository.findByClassroomName(classroomName);
    }
}
