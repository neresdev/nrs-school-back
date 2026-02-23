package com.nrs.school.back.service;

import java.util.*;

import com.nrs.school.back.enm.StudentError;
import com.nrs.school.back.entities.dto.students.StudentDataRequest;
import com.nrs.school.back.entities.dto.students.StudentDataResponse;
import com.nrs.school.back.exceptions.StudentClassroomNotFoundException;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.nrs.school.back.entities.StudentEntity;
import com.nrs.school.back.entities.dto.students.StudentResponse;
import com.nrs.school.back.exceptions.DataIntegrityViolationException;
import com.nrs.school.back.exceptions.ObjectNotFoundException;
import com.nrs.school.back.repository.StudentRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@Component
public class StudentService{

    private static final String NOT_FOUND_MESSAGE = "Student with registration %s not found";
    private static final String JSON_INVALID_MESSAGE = "JSON invalid: ";
    private static final String EXISTING_STUDENT_MESSAGE = "Student with registration %s already exists";
    private static final String STUDENT_CLASSROOM_NOT_FOUND_MESSAGE = "Classroom with name %s does not exist";

    private final StudentRepository repository;
    private final ClassroomService classroomService;
    private final ModelMapper mapper;

    public StudentService(StudentRepository repository, ModelMapper mapper, ClassroomService classroomService) {
        this.repository = repository;
        this.mapper = mapper;
        this.classroomService = classroomService;
    }

    public StudentResponse findAll() {
        final var response = getStudentsMapped();
        return new StudentResponse(response);
    }

    private List<StudentDataResponse> getStudentsMapped() {
        final var entities = repository.findAll();
        return entities.stream().map(this::mapStudent).toList();
    }

    private StudentDataResponse mapStudent(final StudentEntity entity) {
        final var response = mapper.map(entity, StudentDataResponse.class);
        resolveClassroomName(response, entity.getClassroomId());
        return response;
    }

    private void resolveClassroomName(final StudentDataResponse response, @NotNull final Long classroomId) {
        final var classroomName = classroomService.findClassroomNameById(classroomId);
        response.setClassroomName(classroomName);
    }

    public StudentDataResponse findByRegistration(String registration) {
        var entity = repository.findByRegistration(registration).orElseThrow(() -> new ObjectNotFoundException(NOT_FOUND_MESSAGE.formatted(registration)));
        var response = mapper.map(entity, StudentDataResponse.class);

        if (entity.getClassroomId() == null) {
            return response;
        }

        resolveClassroomName(response, entity.getClassroomId());
        return response;
    }

    public StudentResponse findByClassroomReferenceCode(final UUID classroomReferenceCode) {
        var classroom = classroomService.findByClassroomReferenceCode(classroomReferenceCode);
        var entities = repository.findByClassroomId(classroom.getId());
        return new StudentResponse(entities.stream().map(this::mapStudent).toList());
    }

    public StudentDataResponse create(StudentDataRequest request) {
        String messageValidator = entityValidator(request);
        if(!messageValidator.isEmpty()) throw new DataIntegrityViolationException(JSON_INVALID_MESSAGE + messageValidator);
        
        Optional<StudentEntity> existingEntity = repository.findByRegistration(request.getRegistration());

        if(existingEntity.isPresent()) throw new DataIntegrityViolationException(EXISTING_STUDENT_MESSAGE.formatted(existingEntity.get().getRegistration()));

        var entity = mapper.map(request, StudentEntity.class);

        if(request.getClassroomName() != null) {
            var studentClassroom = classroomService.findClassroomByClassroomName(request.getClassroomName());
            if(studentClassroom.isEmpty()) throw new StudentClassroomNotFoundException(STUDENT_CLASSROOM_NOT_FOUND_MESSAGE.formatted(request.getClassroomName()), StudentError.STUDENT_CLASSROOM_NOT_FOUND);
            entity.setClassroomId(studentClassroom.get().getId());
        }
        return mapper.map(repository.save(entity), StudentDataResponse.class);
    }

    public StudentDataResponse update(StudentDataRequest request) {
        String validator = entityValidator(request);
        if(!validator.isEmpty()) throw new DataIntegrityViolationException(JSON_INVALID_MESSAGE + validator);

        var classroomEntity = classroomService.findClassroomByClassroomName(request.getClassroomName());
        if(classroomEntity.isEmpty()) throw new DataIntegrityViolationException(STUDENT_CLASSROOM_NOT_FOUND_MESSAGE.formatted(request.getClassroomName()));

        var studentEntity = mapper.map(request, StudentEntity.class);
        studentEntity.setClassroomId(classroomEntity.get().getId());

        return mapper.map(repository.save(studentEntity), StudentDataResponse.class);
    }

    public void delete(String registration) {
        repository.deleteById(repository.findByRegistration(registration).orElseThrow().getStudentId());
    }

    private String entityValidator(StudentDataRequest studentsDTO){
        String message = "";
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<StudentDataRequest>> violations = validator.validate(studentsDTO);
        if(!violations.isEmpty()){
            for(ConstraintViolation<StudentDataRequest> violation: violations) message += violation.getMessage() + "; ";
        }
        return message;

    }
}
