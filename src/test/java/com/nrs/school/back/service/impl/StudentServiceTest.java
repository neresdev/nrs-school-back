package com.nrs.school.back.service.impl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.nrs.school.back.exceptions.DataIntegratyViolationException;
import com.nrs.school.back.exceptions.ObjectNotFoundException;
import com.nrs.school.back.repository.StudentRepository;
import com.nrs.school.back.service.StudentService;
import com.nrs.school.back.stub.StudentServiceImplStub;


@SpringBootTest
@ActiveProfiles("local")
class StudentServiceTest {

    private static final String DEFAULT_REGISTRATION = "m34m1en";
    private static final String OTHER_REGISTRATION = "w73y4bs";
    private static final String NOT_FOUND_MESSAGE = "Student with registration %s not found";
    private static final List<String> JSON_INVALID_EXPECTED_MESSAGES =  List.of("length must be between 0 and 255", "Registration must have 7 characters");
    private static final String EXISTING_STUDENT_MESSAGE = "Student with registration m34m1en already exists";

    @InjectMocks
    private StudentService service;

    @Mock
    private StudentRepository studentsRepository;

    @Mock
    private ModelMapper mapper;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        mapper = new ModelMapper();
        service = new StudentService(studentsRepository, mapper);
        Mockito.when(studentsRepository.findAll()).thenReturn(StudentServiceImplStub.getStudents());
    }

    @Test
    void whenFindAllThenReturnAllStudents(){
        var result = service.findAll();
        var expectedOutput = StudentServiceImplStub.getStudentsDTO();
        
        expectedOutput.forEach(expectedStudent -> {
            result.stream().filter(studentResult -> studentResult.getStudentId().equals(expectedStudent.getStudentId())).toList().forEach( student -> {
                assertEquals(expectedStudent.getStudentName(), student.getStudentName());
                assertEquals(expectedStudent.getStudentEmail(), student.getStudentEmail());
                assertEquals(expectedStudent.getClassRoomId(), student.getClassRoomId());
                assertEquals(expectedStudent.getRegistration(), student.getRegistration());
            }
            );    
        });
    }

    @Test
    void whenFindByRegistrationThenReturnAStudent(){
        Mockito.when(studentsRepository.findByRegistration(Mockito.any())).thenReturn(Optional.of(StudentServiceImplStub.buildExistingStudentWithThisRegistration()));

        var result = service.findByRegistration(DEFAULT_REGISTRATION);
        var expectedStudent = StudentServiceImplStub.buildExistingStudentDTOWithThisRegistration();
        
        assertEquals(expectedStudent.getStudentName(), result.getStudentName());
        assertEquals(expectedStudent.getStudentEmail(), result.getStudentEmail());
        assertEquals(expectedStudent.getClassRoomId(), result.getClassRoomId());
        assertEquals(expectedStudent.getRegistration(), result.getRegistration());
    }

    @Test
    void whenFindByRegistrationThenThrowObjectNotFoundException(){
        Exception result = assertThrows(ObjectNotFoundException.class, () -> service.findByRegistration(OTHER_REGISTRATION));
        assertEquals(NOT_FOUND_MESSAGE.formatted(OTHER_REGISTRATION), result.getMessage());
    }

    @Test
    void whenCreateThenReturnStudentCreated(){
        Mockito.when(studentsRepository.save(any())).thenReturn(StudentServiceImplStub.buildStudent(2l));
        var result = service.create(StudentServiceImplStub.buildStudentDTO());
        var expectedStudent =  StudentServiceImplStub.buildStudentDTO();

        assertEquals(expectedStudent.getStudentName(), result.getStudentName());
        assertEquals(expectedStudent.getStudentEmail(), result.getStudentEmail());
        assertEquals(expectedStudent.getClassRoomId(), result.getClassRoomId());
        assertEquals(expectedStudent.getRegistration(), result.getRegistration());
    }

    @Test
    void whenCreateThenReturnDataIntegratyViolationExceptionWithJsonInvalidMessage(){
        var invalidStudentsDTO = StudentServiceImplStub.buildInvalidStudentDTO();
        var expectedException = assertThrows(DataIntegratyViolationException.class, () -> service.create(invalidStudentsDTO));
        var actualMessages = new ArrayList<>(Arrays.asList(expectedException.getMessage().replace("JSON invalid: ", "").split(";"))).stream().map(message -> message.trim()).filter(message -> !message.isEmpty()).toList();
        JSON_INVALID_EXPECTED_MESSAGES.forEach(expetedMessage -> assertTrue(actualMessages.contains(expetedMessage)));
    }

    @Test
    void whenCreateThenReturnDataIntegratyViolationExceptionWithExistingStudentMessage(){
        Mockito.when(studentsRepository.findByRegistration(Mockito.any())).thenReturn(Optional.of(StudentServiceImplStub.buildExistingStudentWithThisRegistration()));
        var existingStudent = StudentServiceImplStub.buildExistingStudentDTOWithThisRegistration();
        var expectedException = assertThrows(DataIntegratyViolationException.class, () -> service.create(existingStudent));
        assertEquals(EXISTING_STUDENT_MESSAGE, expectedException.getMessage());
    }

    @Test
    void whenUpdateThenReturnStudentUpdated(){
        Mockito.when(studentsRepository.findByRegistration(Mockito.any())).thenReturn(Optional.of(StudentServiceImplStub.buildExistingStudentWithThisRegistration()));
        Mockito.when(studentsRepository.save(any())).thenReturn(StudentServiceImplStub.buildExistingStudentWithThisRegistration());
        var existingStudentDto = StudentServiceImplStub.buildExistingStudentDTOWithThisRegistration();
        var result = service.update(existingStudentDto);
        assertEquals(existingStudentDto.getStudentName(), result.getStudentName());
        assertEquals(existingStudentDto.getStudentEmail(), result.getStudentEmail());
        assertEquals(existingStudentDto.getClassRoomId(), result.getClassRoomId());
        assertEquals(existingStudentDto.getRegistration(), result.getRegistration());


        
    }
}
