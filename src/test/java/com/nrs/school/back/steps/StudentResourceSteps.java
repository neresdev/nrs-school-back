package com.nrs.school.back.steps;

import com.nrs.school.back.SpringIntegrationTest;
import com.nrs.school.back.controller.StudentResource;
import com.nrs.school.back.entities.dto.StudentDTO;
import com.nrs.school.back.exceptions.ObjectNotFoundException;
import com.nrs.school.back.repository.StudentRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TODO:
 *  1 - Use .feature objects sent the "then" cases
 */
public class StudentResourceSteps extends SpringIntegrationTest {

    private static final String NOT_FOUND_MESSAGE = "Student with registration %s not found";

    private final StudentResource studentResource;

    private ResponseEntity<List<StudentDTO>> studentsReturned;

    private ResponseEntity<StudentDTO> studentReturned;

    public StudentResourceSteps(StudentResource studentResource, StudentRepository studentRepository) {
        this.studentResource = studentResource;
    }

    @When("find student by registration {string}")
    public void findStudentByRegistration(String registration) {
        this.studentReturned = studentResource.findStudentByRegistration(registration);
    }

    @When("find all student")
    public void findAllStudent() {
        this.studentsReturned = studentResource.findAll();
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
                                                    .orElseThrow(()-> new ObjectNotFoundException(NOT_FOUND_MESSAGE))));

    }


    @Then("return a student in database")
    public void returnAStudentInDatabase(List<Map<String, String>> expectedData) {
        final var expectedStudent = expectedData.stream().map(this::convertFeatureDataToStudentDto).toList().get(0);
        final var actualStudent = studentReturned.getBody();

        assertNotNull(actualStudent);
        assertFields(expectedStudent, actualStudent);
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
