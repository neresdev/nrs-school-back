package com.nrs.school.back.steps;

import com.nrs.school.back.SpringIntegrationTest;
import com.nrs.school.back.controller.StudentResource;
import com.nrs.school.back.entities.Student;
import com.nrs.school.back.entities.dto.StudentDTO;
import com.nrs.school.back.exceptions.ObjectNotFoundException;
import com.nrs.school.back.repository.StudentRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.hibernate.ObjectDeletedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class StudentResourceSteps extends SpringIntegrationTest {

    private static final String NOT_FOUND_MESSAGE = "Student with registration %s not found";

    private final StudentResource studentResource;

    private ResponseEntity<List<StudentDTO>> studentsReturned;

    public StudentResourceSteps(StudentResource studentResource, StudentRepository studentRepository) {
        this.studentResource = studentResource;
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

    private void assertFields(StudentDTO expected, StudentDTO actual) {
        assertEquals(expected.getStudentId(), actual.getStudentId());
        assertEquals(expected.getStudentName(), actual.getStudentName());
        assertEquals(expected.getStudentEmail(), actual.getStudentEmail());
        assertEquals(expected.getClassRoomId(), actual.getClassRoomId());
        assertEquals(expected.getRegistration(), actual.getRegistration());
    }

}
