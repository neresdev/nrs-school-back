package com.nrs.school.back.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.nrs.school.back.entities.dto.StudentsDTO;
import com.nrs.school.back.repository.StudentsRepository;
import com.nrs.school.back.stub.StudentsServiceImplStub;

@SpringBootTest
class StudentsServiceImplTest {

    @InjectMocks
    private StudentsServiceImpl service;

    @Mock
    private StudentsRepository studentsRepository;

    @Mock
    private ModelMapper mapper;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        mapper = new ModelMapper();
        when(studentsRepository.findAll()).thenReturn(Collections.singletonList(StudentsServiceImplStub.getStudents()));
        service = new StudentsServiceImpl(studentsRepository, mapper);
    }

    @Test
    void whenFindAllThenReturnAllStudents(){
        var result = service.findAll();
        var expectedOutput = Collections.singletonList(StudentsServiceImplStub.getStudentsDTO());
        assertEquals(expectedOutput, result);
    }

}
