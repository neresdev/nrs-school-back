package com.nrs.school.back.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nrs.school.back.StepDefinitionsDefault;
import com.nrs.school.back.entities.dto.student.StudentDataRequest;
import com.nrs.school.back.entities.dto.student.StudentDataResponse;
import com.nrs.school.back.entities.dto.student.StudentResponse;
import com.nrs.school.back.exceptions.DataIntegrityViolationException;
import com.nrs.school.back.exceptions.ObjectNotFoundException;
import com.nrs.school.back.fixtures.TestApiFixtures;
import com.nrs.school.back.resource.StudentResource;

public class StudentResourceSteps extends StepDefinitionsDefault {

    @Value("${server.port}")
    private String port;

    private static final String NOT_FOUND_MESSAGE = "Student with registration %s not found";

    private final StudentResource studentResource;

    private ResponseEntity<StudentResponse> studentsResponse;

    private ResponseEntity<StudentDataResponse> studentDataResponse;

    private ResponseEntity<StudentDataResponse> studentCreated;

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

    @When("create a single student")
    public void createStudent(List<StudentDataRequest> students) throws URISyntaxException {
        assertEquals(1, students.size(), "create a single student must have only 1 student");
        studentDataResponse = testApiFixtures.makePostRequest(StudentResource.BASE_PATH, students.get(0), StudentDataResponse.class, true);
    }

    @When("update student")
    public void updateStudent(List<StudentDataRequest> request) throws URISyntaxException {
        studentDataResponse = testApiFixtures.makePutRequest(StudentResource.BASE_PATH, request.get(0), StudentDataResponse.class);
    }

    @When("delete student by registration {string}")
    public void deleteStudent(String registration) throws URISyntaxException {
        final var deletePath =  StudentResource.BASE_PATH + "/" + registration;
        studentDataResponse = testApiFixtures.makeDeleteRequest(deletePath, StudentDataResponse.class);
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

    @Then("return a response with status code {int}")
    public void throwAnException(int statusCode) {
        assertEquals(HttpStatus.valueOf(statusCode), studentDataResponse.getStatusCode());
    }

    @Then("return a url form the student updated with registration {string}")
    @Then("return a url form the student created with registration {string}")
    public void returnAUrlStudent(String registration) throws URISyntaxException {
        final var localhost = "http://localhost:%s".formatted(port);
        final var expectedLocation = new URI(localhost + StudentResource.BASE_PATH + "/" + registration);
        final var actualLocation = studentDataResponse.getHeaders().getLocation();
        assertEquals(expectedLocation, actualLocation);
    }

    @Then("return a bad request status code")
    public void throwAnDataIntegratyViolationException() {
        assertEquals(HttpStatus.BAD_REQUEST, studentDataResponse.getStatusCode());
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

    private StudentDataResponse convertFeatureDataToStudentResponse(Map<String, String> data) {
        return new StudentDataResponse(
                data.get("studentName"),
                data.get("studentEmail"),
                data.get("classroomName"),
                data.get("registration")
        );
    }

    private StudentDataRequest convertFeatureDataToStudentRequest(Map<String, String> data) {
        return new StudentDataRequest(
                data.get("studentName"),
                data.get("studentEmail"),
                data.get("classroomName"),
                data.get("registration")
        );
    }

    @DataTableType
    public StudentDataRequest convertToDataRequest(Map<String, String> map) {
        final var request = new StudentDataRequest();

        request.setStudentName(map.get("studentName"));
        request.setStudentEmail(map.get("studentEmail"));
        request.setClassroomName(map.get("classroomName"));
        request.setRegistration(map.get("registration"));

        return request;
    }

}
