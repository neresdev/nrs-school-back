package com.nrs.school.back.steps;

import com.nrs.school.back.StepDefinitionsDefault;
import com.nrs.school.back.entities.dto.students.StudentDataRequest;
import com.nrs.school.back.entities.dto.students.StudentDataResponse;
import com.nrs.school.back.entities.dto.students.StudentResponse;
import com.nrs.school.back.exceptions.DataIntegrityViolationException;
import com.nrs.school.back.exceptions.ObjectNotFoundException;
import com.nrs.school.back.fixtures.TestApiFixtures;
import com.nrs.school.back.resource.StudentResource;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class StudentResourceSteps extends StepDefinitionsDefault {

    private static final String NOT_FOUND_MESSAGE = "Student with registration %s not found";

    private final StudentResource studentResource;

    private ResponseEntity<StudentResponse> studentsResponse;

    private ResponseEntity<StudentDataResponse> studentDataResponse;

    private ResponseEntity<StudentDataResponse> studentCreated;

    private DataIntegrityViolationException dataIntegrityViolationInvalidEmailException;

    private DataIntegrityViolationException dataIntegrityViolationExistingRegistrationException;

    private ResponseEntity<StudentDataResponse> studentUpdated;

    private final TestApiFixtures testApiFixtures;

    public StudentResourceSteps(StudentResource studentResource, TestApiFixtures testApiFixtures) {
        this.studentResource = studentResource;
        this.testApiFixtures = testApiFixtures;
    }

    @When("the client sends a GET http request get all students")
    public void findAllStudent() throws URISyntaxException {
        this.studentsResponse = testApiFixtures.makeGetRequest(StudentResource.BASE_PATH, StudentResponse.class);
    }

    @When("the client sends a GET http request a student with registration {string}")
    public void findStudentByRegistration(String registration) throws URISyntaxException {
        final var registrationPath = "/" + registration;
        studentDataResponse = testApiFixtures.makeGetRequest(StudentResource.BASE_PATH + registrationPath, StudentDataResponse.class);
    }

    @When("create a student")
    public void createStudent(List<Map<String, String>> student) { // todo wip
        try {
            this.studentCreated = this.studentResource.create(convertFeatureDataToStudentRequest(student.get(0)));
        } catch (DataIntegrityViolationException e) {
            this.dataIntegrityViolationInvalidEmailException = e;
        }
    }

    @When("update student")
    public void updateStudent(List<Map<String, String>> expectedData) {
        var studentToUpdate = convertFeatureDataToStudentRequest(expectedData.get(0));
        this.studentUpdated = studentResource.update(studentToUpdate);
    }

    @When("delete student by registration {string}")
    public void deleteStudentByRegistration(String registration) {
        this.studentResource.delete(registration);
    }

    @And("create a student with existing registration")
    public void createAStudentWithExistingRegistration(List<Map<String, String>> student) {
        try {
            this.studentResource.create(convertFeatureDataToStudentRequest(student.get(0)));
        } catch (DataIntegrityViolationException e){
            this.dataIntegrityViolationExistingRegistrationException = e;
        }

    }

    @Then("return all students")
    public void returnAllStudents(List<Map<String, String>> expectedData) {
        final var expectedStudents = expectedData.stream().map(this::convertFeatureDataToStudentResponse).toList();

        assertTrue(studentsResponse.getStatusCode().is2xxSuccessful());

        Objects.requireNonNull(studentsResponse.getBody())
            .getStudents()
            .forEach(student -> assertFields(expectedStudents.stream().filter(
                    s -> Objects.equals(s.getRegistration(), student.getRegistration()))
                            .findFirst().orElseThrow(()-> new ObjectNotFoundException(NOT_FOUND_MESSAGE.formatted(student.getRegistration()))),
                        student));

    }

    @Then("return a student")
    public void returnAStudent(List<Map<String, String>> expectedData) {
        final var expectedStudent = expectedData.stream().map(this::convertFeatureDataToStudentResponse).toList().get(0);

        assertTrue(studentDataResponse.getStatusCode().is2xxSuccessful());
        final var actualStudent = studentDataResponse.getBody();

        assertNotNull(actualStudent);
        assertFields(expectedStudent, actualStudent);
    }

    @Then("throw an student not found with registration {string} not found")
    public void throwAnException(String registration) {
        assertEquals(HttpStatus.NOT_FOUND, studentDataResponse.getStatusCode());
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
        var expectedStudent = this.convertFeatureDataToStudentResponse(expectedData.get(0));
        final var expectedLocation = "http://localhost:8080/api/v1/get/student/%s".formatted(expectedStudent.getRegistration());
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

    private static void assertFields(StudentDataResponse expected, StudentDataResponse actual) {
        assertEquals(expected.getStudentName(), actual.getStudentName());
        assertEquals(expected.getStudentEmail(), actual.getStudentEmail());
        assertEquals(expected.getClassroomName(), actual.getClassroomName());
        assertEquals(expected.getRegistration(), actual.getRegistration());
    }

    private StudentDataResponse convertFeatureDataToStudentResponse(Map<String, String> data){
        return new StudentDataResponse(
                data.get("studentName"),
                data.get("studentEmail"),
                data.get("classroomName"),
                data.get("registration")
        );
    }

    private StudentDataRequest convertFeatureDataToStudentRequest(Map<String, String> data){
        return new StudentDataRequest(
                data.get("studentName"),
                data.get("studentEmail"),
                data.get("classroomName"),
                data.get("registration")
        );
    }



}
