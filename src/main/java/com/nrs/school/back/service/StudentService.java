package com.nrs.school.back.service;

import java.util.*;
import java.util.stream.Collectors;

import com.nrs.school.back.enm.StudentError;
import com.nrs.school.back.exceptions.StudentClassroomNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.nrs.school.back.entities.Student;
import com.nrs.school.back.entities.dto.StudentDTO;
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

    public List<StudentDTO> findAll() {
        return repository.findAll().stream().map(s -> {
            var classroomName = classroomService.findById(s.getClassroomId()).getClassroomName();
            var student = mapper.map(s, StudentDTO.class);
            student.setClassroomName(classroomName);
            return student;
        }).collect(Collectors.toList());
    }

    public StudentDTO findByRegistration(String registration) {
        var student = repository.findByRegistration(registration).orElseThrow(() -> new ObjectNotFoundException(NOT_FOUND_MESSAGE.formatted(registration)));

        var classroomName = classroomService.findById(student.getClassroomId()).getClassroomName();

        var studentDTO = mapper.map(student, StudentDTO.class);
        studentDTO.setClassroomName(classroomName);

        return studentDTO;
    }

    public List<StudentDTO> findByClassroomId(String classroomId) {
        var classroom = classroomService.findByClassroomId(classroomId);
        var students = repository.findByClassroomId(classroom.getId());

        return students.stream().map(s -> {
            var student = mapper.map(s, StudentDTO.class);
            student.setClassroomName(classroom.getClassroomName());
            return mapper.map(student, StudentDTO.class);
        }).toList();
    }

    public StudentDTO create(StudentDTO studentDTO) {
        String messageValidator = entityValidator(studentDTO);
        if(!messageValidator.isEmpty()) throw new DataIntegrityViolationException(JSON_INVALID_MESSAGE + messageValidator);
        
        Optional<Student> student = repository.findByRegistration(studentDTO.getRegistration());

        if(student.isPresent()) throw new DataIntegrityViolationException(EXISTING_STUDENT_MESSAGE.formatted(student.get().getRegistration()));

        var studentEntity = mapper.map(studentDTO, Student.class);

        if(studentDTO.getClassroomName() != null) {
            var studentClassroom = classroomService.findClassroomByClassroomName(studentDTO.getClassroomName());
            if(studentClassroom.isEmpty()) throw new StudentClassroomNotFoundException(STUDENT_CLASSROOM_NOT_FOUND_MESSAGE.formatted(studentDTO.getClassroomName()), StudentError.STUDENT_CLASSROOM_NOT_FOUND);
            studentEntity.setClassroomId(studentClassroom.get().getId());
        }

        return mapper.map(repository.save(studentEntity), StudentDTO.class);
    }

    public StudentDTO update(StudentDTO studentDTO) {
        String messageValidator = entityValidator(studentDTO);
        if(!messageValidator.isEmpty()) throw new DataIntegrityViolationException(JSON_INVALID_MESSAGE + messageValidator);
        
        studentDTO.setStudentId(findByRegistration(studentDTO.getRegistration()).getStudentId());

        var studentClassroom = classroomService.findClassroomByClassroomName(studentDTO.getClassroomName());
        if(studentClassroom.isEmpty()) throw new DataIntegrityViolationException(STUDENT_CLASSROOM_NOT_FOUND_MESSAGE.formatted(studentDTO.getClassroomName()));

        var studentEntity = mapper.map(studentDTO, Student.class);
        studentEntity.setClassroomId(studentClassroom.get().getId());

        return mapper.map(repository.save(studentEntity), StudentDTO.class);
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
