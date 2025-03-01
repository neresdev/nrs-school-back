package com.nrs.school.back.steps;

import com.nrs.school.back.SpringIntegrationTest;
import com.nrs.school.back.controller.StudentResource;
import com.nrs.school.back.entities.dto.StudentDTO;
import com.nrs.school.back.exceptions.ObjectNotFoundException;
import com.nrs.school.back.repository.StudentRepository;
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

    public StudentResourceSteps(StudentResource studentResource, StudentRepository studentRepository) {
        this.studentResource = studentResource;
    }

    @When("find student by registration {string}")
    public void findStudentByRegistration(String registration) {
        try {
            this.studentReturned = studentResource.findStudentByRegistration(registration);
        } catch (Exception e){
            this.objectNotFound = (ObjectNotFoundException) e;
        }

    }

    @When("find all student")
    public void findAllStudent() {
        this.studentsReturned = studentResource.findAll();
    }

    @When("create a student")
    public void createStudent(List<Map<String, String>> student) {
        this.studentCreated = this.studentResource.create(convertFeatureDataToStudentDto(student.get(0)));
    }

    @Then("return all students in database")
    public void returnAllStudentsInDatabase(List<Map<String, String>> expectedData) {
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


    @Then("return a student in database")
    public void returnAStudentInDatabase(List<Map<String, String>> expectedData) {
        final var expectedStudent = expectedData.stream().map(this::convertFeatureDataToStudentDto).toList().get(0);
        final var actualStudent = studentReturned.getBody();

        assertNotNull(actualStudent);
        assertFields(expectedStudent, actualStudent);
    }

    @Then("throw an student with registration {string} not found")
    public void throwAnException(String registration) {
        assertEquals(objectNotFound.getMessage(), NOT_FOUND_MESSAGE.formatted(registration));
    }

    @Then("return a student created")
    public void returnAStudentCreated(List<Map<String, String>> expectedData) {
        final var expectedLocation = "http://localhost:8080/api/v1/get/student/1";
        assertEquals(expectedLocation, Objects.requireNonNull(this.studentCreated.getHeaders().get("Location")).get(0));
    }

    private void assertFields(StudentDTO expected, StudentDTO actual) {
        assertEquals(expected.getStudentId(), actual.getStudentId());
        assertEquals(expected.getStudentName(), actual.getStudentName());
        assertEquals(expected.getStudentEmail(), actual.getStudentEmail());
        assertEquals(expected.getClassRoomId(), actual.getClassRoomId());
        assertEquals(expected.getRegistration(), actual.getRegistration());
    }

    private StudentDTO convertFeatureDataToStudentDto(Map<String, String> data){
        return new StudentDTO(
                Long.valueOf(data.get("studentId")),
                data.get("studentName"),
                data.get("studentEmail"),
                Long.valueOf(data.get("classRoomId")),
                data.get("registration")
        );
    }

}
