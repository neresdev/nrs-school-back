// package com.nrs.school.back.controller;

// import org.junit.jupiter.api.Assertions;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.HttpStatus;
// import org.springframework.test.context.ActiveProfiles;

// import com.nrs.school.back.stub.StudentServiceImplStub;
// import com.nrs.school.back.repository.StudentRepository;
// import com.nrs.school.back.service.StudentService;


// @SpringBootTest
// @ActiveProfiles("local")
// class StudentsControllerTest {

//     private static final String DEFAULT_REGISTRATION = "m423af1";

//     @Autowired
//     private StudentController controller;

//     @Autowired
//     private StudentService studentService;

//     @Autowired
//     private StudentRepository studentsRepository;

//     @Test
//     void whenFindAllThenReturnAllStudents(){
//         var controllerResult  = controller.findAll();
//         var expectedControllerResult = StudentServiceImplStub.getStudentsDTO();
//         Assertions.assertEquals(HttpStatus.OK.value(), controllerResult.getStatusCode().value());
//         Assertions.assertEquals(expectedControllerResult, controllerResult.getBody());
//     }

//     @Test
//     void whenFindStudentByRegistrationThenReturnStudent(){
//         var controllerResult  = controller.findStudentByRegistration(DEFAULT_REGISTRATION);
//         var expectedControllerResult = StudentServiceImplStub.getStudentsDTO().get(0);
//         Assertions.assertEquals(HttpStatus.OK.value(), controllerResult.getStatusCode().value());
//         Assertions.assertEquals(expectedControllerResult, controllerResult.getBody());
//     }

//     @Test
//     void whenCreateStudentThenReturnStudentCreated(){
//         var studentToBeCreated = StudentServiceImplStub.buildStudentDTO();
//         var controllerResult = controller.create(studentToBeCreated);

//         Assertions.assertEquals(studentToBeCreated, controllerResult.getBody());
//     }
// }
