package com.nrs.school.back.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.nrs.school.back.entities.Students;
import com.nrs.school.back.entities.dto.StudentsDTO;
import com.nrs.school.back.exceptions.ObjectNotFoundException;
import com.nrs.school.back.repository.StudentsRepository;
import com.nrs.school.back.stub.StudentsServiceImplStub;

import lombok.RequiredArgsConstructor;

@SpringBootTest
@ActiveProfiles("local")
class StudentsServiceImplTest {

    private static final String DEFAULT_REGISTRATION = "m34m1en";
    private static final String OTHER_REGISTRATION = "w73y4bs";
    private static final String NOT_FOUND_MESSAGE = "Student with registration %s not found";

    @Autowired
    private StudentsServiceImpl service;

    @Autowired
    private StudentsRepository studentsRepository;

    @Autowired
    private ModelMapper mapper;

    @Test
    void whenFindAllThenReturnAllStudents(){
        var result = service.findAll();
        var expectedOutput = StudentsServiceImplStub.getStudentsDTO();
        assertEquals(expectedOutput, result);
    }

    @Test
    void whenFindByRegistrationThenReturnAStudent(){
        var result = service.findByRegistration(DEFAULT_REGISTRATION);
        var expectedOutput = StudentsServiceImplStub.getStudentsDTO().get(1);
        assertEquals(expectedOutput, result);
    }

    @Test
    void whenFindByRegistrationThenThrowObjectNotFoundException(){
        Exception result = assertThrows(ObjectNotFoundException.class, () -> service.findByRegistration(OTHER_REGISTRATION));
        assertEquals(String.format(NOT_FOUND_MESSAGE, OTHER_REGISTRATION), result.getMessage());
    }

    @Test
    void whenCreateThenReturnStudentCreated(){
        var result = service.create(StudentsServiceImplStub.buildStudentDTO());
        var repositoryResult = studentsRepository.findByRegistration(StudentsServiceImplStub.buildStudentDTO().getRegistration());

        assertEquals(result, StudentsServiceImplStub.buildStudentDTO());
        assertEquals(repositoryResult.get(), StudentsServiceImplStub.buildStudent());

    }

}
