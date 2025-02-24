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
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

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
    public void returnAllStudentsInDatabase() {
        final var expectedStudents = List.of(
                new StudentDTO(1L, "Student 1", "student1@fakeemail.com", 1L, "m423af1"),
                new StudentDTO(2L, "Student 2", "student2@fakeemail.com", 1L, "m34m1en")
        );

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
    public void returnAStudentInDatabase() {
        final var expectedStudent = new StudentDTO(2L, "Student 2", "student2@fakeemail.com", 1L, "m34m1en");
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

}
