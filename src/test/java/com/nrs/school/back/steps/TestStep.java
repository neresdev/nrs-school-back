package com.nrs.school.back.steps;

import com.nrs.school.back.SpringIntegrationTest;
import com.nrs.school.back.entities.Student;
import com.nrs.school.back.repository.StudentRepository;
import com.nrs.school.back.service.StudentService;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDateTime;

public class TestStep extends SpringIntegrationTest {
    private final StudentService studentService;
    private final StudentRepository studentRepository;

    public TestStep (StudentService studentService, StudentRepository studentRepository) {
        this.studentService = studentService;
        this.studentRepository = studentRepository;
    }
    @Before
    public void setUp() {
        Student student = new Student();
        student.setStudentName("John");
        student.setStudentEmail("john@gmail.com");
        student.setClassroomId(1l);
        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());
        this.studentRepository.save(student);

    }

    @When("test when")
    public void when() {

        int a = 0;

    }

    @Then("test then")
    public void then() {

    }

    @And("test and")
    public void and() {

    }
}
