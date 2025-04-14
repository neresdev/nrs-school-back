package com.nrs.school.back.steps;

import com.nrs.school.back.SpringIntegrationTest;
import com.nrs.school.back.exceptions.DataIntegrityViolationException;
import com.nrs.school.back.resource.StudentResource;
import com.nrs.school.back.entities.dto.StudentDTO;
import com.nrs.school.back.exceptions.ObjectNotFoundException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class StudentResourceSteps extends SpringIntegrationTest {

    private static final String NOT_FOUND_MESSAGE = "Student with registration %s not found";

    private final StudentResource studentResource;

    private ResponseEntity<List<StudentDTO>> studentsReturned;

    private ResponseEntity<StudentDTO> studentReturned;

    private ObjectNotFoundException objectNotFound;

    private ResponseEntity<StudentDTO> studentCreated;

    private DataIntegrityViolationException dataIntegrityViolationInvalidEmailException;

    private DataIntegrityViolationException dataIntegrityViolationExistingRegistrationException;

    private ResponseEntity<StudentDTO> studentUpdated;

    public StudentResourceSteps(StudentResource studentResource) {
        this.studentResource = studentResource;
    }

    @When("find student by registration {string}")
    public void findStudentByRegistration(String registration) {
        try {
            this.studentReturned = studentResource.findStudentByRegistration(registration);
        } catch (ObjectNotFoundException e){
            this.objectNotFound = e;
        }

    }

    @When("find all student")
    public void findAllStudent() {
        this.studentsReturned = studentResource.findAll();
    }

    @When("create a student")
    public void createStudent(List<Map<String, String>> student) {
        try {
            this.studentCreated = this.studentResource.create(convertFeatureDataToStudentDto(student.get(0)));
        } catch (DataIntegrityViolationException e) {
            this.dataIntegrityViolationInvalidEmailException = e;
        }
    }

    @When("update student")
    public void updateStudent(List<Map<String, String>> expectedData) {
        var studentToUpdate = convertFeatureDataToStudentDto(expectedData.get(0));
        this.studentUpdated = studentResource.update(studentToUpdate);
    }

    @When("delete student by registration {string}")
    public void deleteStudentByRegistration(String registration) {
        int a = 0;
        this.studentResource.delete(registration);
    }

    @And("create a student with existing registration")
    public void createAStudentWithExistingRegistration(List<Map<String, String>> student) {
        try {
            this.studentResource.create(convertFeatureDataToStudentDto(student.get(0)));
        } catch (DataIntegrityViolationException e){
            this.dataIntegrityViolationExistingRegistrationException = e;
        }

    }

    @Then("return all students")
    public void returnAllStudents(List<Map<String, String>> expectedData) {
        final var expectedStudents = expectedData.stream().map(this::convertFeatureDataToStudentDto).toList();

        assertTrue(studentsReturned.getStatusCode().is2xxSuccessful());

        Objects.requireNonNull(studentsReturned.getBody())
                .forEach(student ->
                        assertFields(student, expectedStudents
                                                .stream()
                                                .filter(s -> Objects.equals(s.getStudentId(), student.getStudentId()))
                                                    .findFirst()
                                                    .orElseThrow(()-> new ObjectNotFoundException(NOT_FOUND_MESSAGE.formatted(student.getRegistration())))));

    }

    @Then("return a student")
    public void returnAStudent(List<Map<String, String>> expectedData) {
        final var expectedStudent = expectedData.stream().map(this::convertFeatureDataToStudentDto).toList().get(0);
        final var actualStudent = studentReturned.getBody();

        assertNotNull(actualStudent);
        assertFields(expectedStudent, actualStudent);
    }

    @Then("throw an student not found with registration {string} not found")
    public void throwAnException(String registration) {
        assertEquals(objectNotFound.getMessage(), NOT_FOUND_MESSAGE.formatted(registration));
    }

    @Then("return a url form the student created")
    public void returnAStudentCreated() {
        final var expectedLocation = "http://localhost:8080/api/v1/get/student/1";
        assertEquals(expectedLocation, Objects.requireNonNull(this.studentCreated.getHeaders().get("Location")).get(0));
    }

    @Then("throw an data integrity violation exception")
    public void throwAnDataIntegratyViolationException() {
        var expectedMessage = "JSON invalid: Email cannot be blank or null; ";
        assertEquals(expectedMessage, dataIntegrityViolationInvalidEmailException.getMessage());
    }

    @Then("return an student already exists exception")
    public void throwAnDataIntegrityViolationExistingRegistrationException() {
        var expectedMessage = "Student with registration m423af2 already exists";
        assertEquals(expectedMessage, dataIntegrityViolationExistingRegistrationException.getMessage());
    }

    @Then("return a url form the student with fields updated")
    public void thenReturnAUrlFromStudentUpdated(List<Map<String, String>> expectedData) {
        var expectedStudent = this.convertFeatureDataToStudentDto(expectedData.get(0));
        final var expectedLocation = "http://localhost:8080/api/v1/get/student/%s".formatted(expectedStudent.getStudentId());
        var student = this.studentResource.findStudentByRegistration(expectedStudent.getRegistration());

        assertEquals(expectedLocation, Objects.requireNonNull(this.studentUpdated.getHeaders().get("Location")).get(0));
        assertFields(expectedStudent, Objects.requireNonNull(student.getBody()));

    }

    @Then("there must be no students with registration {string}")
    public void noOneStudentWithRegistration(String registration) {
        try {
           studentResource.findStudentByRegistration(registration);
        } catch (ObjectNotFoundException e){
            var expectedMessage = NOT_FOUND_MESSAGE.formatted(registration);
            assertEquals(expectedMessage, e.getMessage());
        }

    }

    private static void assertFields(StudentDTO expected, StudentDTO actual) {
        assertEquals(expected.getStudentId(), actual.getStudentId());
        assertEquals(expected.getStudentName(), actual.getStudentName());
        assertEquals(expected.getStudentEmail(), actual.getStudentEmail());
        assertEquals(expected.getClassroomName(), actual.getClassroomName());
        assertEquals(expected.getRegistration(), actual.getRegistration());
    }

    private StudentDTO convertFeatureDataToStudentDto(Map<String, String> data){
        return new StudentDTO(
                Long.valueOf(data.get("studentId")),
                data.get("studentName"),
                data.get("studentEmail"),
                data.get("classroomName"),
                data.get("registration")
        );
    }

}
