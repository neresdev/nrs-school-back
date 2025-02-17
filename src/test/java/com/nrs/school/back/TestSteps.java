package com.nrs.school.back;

import com.nrs.school.back.entities.Student;
import com.nrs.school.back.service.StudentService;
import io.cucumber.java.en.Given;

public class TestSteps {
    private final StudentService studentService;

    public TestSteps(StudentService studentService) {
        this.studentService = studentService;
    }

    @Given("a test")
    public void aTest() {
        Student student = new Student();
        int a = 0;
    }
}
