package com.nrs.school.back.controller;
import com.nrs.school.back.controller.StudentController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import com.nrs.school.back.repository.StudentRepository;
import com.nrs.school.back.service.StudentService;
import com.nrs.school.back.stub.StudentServiceImplStub;


@SpringBootTest
@ActiveProfiles("local")
class StudentsControllerTest {

    private static final String DEFAULT_REGISTRATION = "m423af1";

    @InjectMocks
    private StudentController controller;

    @Mock
    private StudentService studentService;

    @Mock
    private StudentRepository studentsRepository;

    @Mock 
    private ModelMapper modelMapper;
    
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        studentService = new StudentService(studentsRepository, modelMapper);
        controller = new StudentController(studentService);
        Mockito.when(studentService.findAll()).thenReturn(StudentServiceImplStub.getStudentsDTO());
    }

    @Test
    void whenFindAllThenReturnAllStudents(){
        var controllerResult  = controller.findAll();
        var expectedControllerResult = StudentServiceImplStub.getStudentsDTO();
        Assertions.assertEquals(HttpStatus.OK.value(), controllerResult.getStatusCode().value());
        Assertions.assertEquals(expectedControllerResult, controllerResult.getBody());
    }

    @Test
    void whenFindStudentByRegistrationThenReturnStudent(){
        var controllerResult  = controller.findStudentByRegistration(DEFAULT_REGISTRATION);
        var expectedControllerResult = StudentServiceImplStub.getStudentsDTO().get(0);
        Assertions.assertEquals(HttpStatus.OK.value(), controllerResult.getStatusCode().value());
        Assertions.assertEquals(expectedControllerResult, controllerResult.getBody());
    }

    @Test
    void whenCreateStudentThenReturnStudentCreated(){
        var studentToBeCreated = StudentServiceImplStub.buildStudentDTO();
        var controllerResult = controller.create(studentToBeCreated);

        Assertions.assertEquals(studentToBeCreated, controllerResult.getBody());
    }
}
