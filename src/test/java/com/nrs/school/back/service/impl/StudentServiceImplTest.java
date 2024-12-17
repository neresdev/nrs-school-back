// package com.nrs.school.back.service.impl;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.junit.jupiter.api.Assertions.assertTrue;

// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.ActiveProfiles;

// import com.nrs.school.back.exceptions.DataIntegratyViolationException;
// import com.nrs.school.back.exceptions.ObjectNotFoundException;
// import com.nrs.school.back.repository.StudentRepository;
// import com.nrs.school.back.stub.StudentServiceImplStub;


// @SpringBootTest
// @ActiveProfiles("local")
// class StudentServiceImplTest {

//     private static final String DEFAULT_REGISTRATION = "m34m1en";
//     private static final String OTHER_REGISTRATION = "w73y4bs";
//     private static final String NOT_FOUND_MESSAGE = "Student with registration %s not found";
//     private static final String JSON_INVALID_MESSAGE = "JSON invalid: Registration must have 7 characters; length must be between 0 and 255; ";
//     private static final String EXISTING_STUDENT_MESSAGE = "Student with registration m34m1en already exists";

//     @Autowired
//     private StudentServiceImpl service;

//     @Autowired
//     private StudentRepository studentsRepository;

//     @Test
//     void whenFindAllThenReturnAllStudents(){
//         var result = service.findAll();
//         var expectedOutput = StudentServiceImplStub.getStudentsDTO();
//         assertEquals(expectedOutput, result);
//     }

//     @Test
//     void whenFindByRegistrationThenReturnAStudent(){
//         var result = service.findByRegistration(DEFAULT_REGISTRATION);
//         var expectedOutput = StudentServiceImplStub.getStudentsDTO().get(1);
//         assertEquals(expectedOutput, result);
//     }

//     @Test
//     void whenFindByRegistrationThenThrowObjectNotFoundException(){
//         Exception result = assertThrows(ObjectNotFoundException.class, () -> service.findByRegistration(OTHER_REGISTRATION));
//         assertEquals(NOT_FOUND_MESSAGE.formatted(OTHER_REGISTRATION), result.getMessage());
//     }

//     @Test
//     void whenCreateThenReturnStudentCreated(){
//         var result = service.create(StudentServiceImplStub.buildStudentDTO());
//         var repositoryResult = studentsRepository.findByRegistration(StudentServiceImplStub.buildStudentDTO().getRegistration());
//         assertEquals(result, StudentServiceImplStub.buildStudentDTO());
//         assertEquals(repositoryResult.get(), StudentServiceImplStub.buildStudent());

//     }

//     @Test
//     void whenCreateThenReturnDataIntegratyViolationExceptionWithJsonInvalidMessage(){
//         var invalidStudentsDTO = StudentServiceImplStub.buildInvalidStudentDTO();
//         var expectedException = assertThrows(DataIntegratyViolationException.class, () -> service.create(invalidStudentsDTO));
//         assertEquals(JSON_INVALID_MESSAGE, expectedException.getMessage());
//     }

//     @Test
//     void whenCreateThenReturnDataIntegratyViolationExceptionWithExistingStudentMessage(){
//         var existingStudent = StudentServiceImplStub.buildExistingStudentDTOWithThisRegistration();
//         var expectedException = assertThrows(DataIntegratyViolationException.class, () -> service.create(existingStudent));
//         assertEquals(EXISTING_STUDENT_MESSAGE, expectedException.getMessage());
//     }

//     @Test
//     void whenUpdateThenReturnStudentUpdated(){
//         var existingStudentDto = StudentServiceImplStub.buildExistingStudentDTOWithThisRegistration();
//         var existingStudent = StudentServiceImplStub.buildExistingStudentWithThisRegistration();
//         var result = service.update(existingStudentDto);
//         var repositoryResult = studentsRepository.findByRegistration(existingStudent.getRegistration());

//         assertEquals(existingStudentDto, result);
        
//         assertTrue(repositoryResult.isPresent());
//         assertEquals(existingStudent, repositoryResult.get());
        
//     }
// }
