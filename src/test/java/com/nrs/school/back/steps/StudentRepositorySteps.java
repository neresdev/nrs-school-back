package com.nrs.school.back.steps;

import com.nrs.school.back.StepDefinitionsDefault;
import com.nrs.school.back.entities.StudentEntity;
import com.nrs.school.back.repository.StudentRepository;
import io.cucumber.java.en.Given;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class StudentRepositorySteps extends StepDefinitionsDefault {

    private final StudentRepository studentRepository;

    public StudentRepositorySteps(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Given("student in database")
    public void insertStudent(List<Map<String, String>> expectedData){
        studentRepository.saveAll(expectedData.stream().map(this::convertFeatureDataToStudentEntity).toList());
    }

    private StudentEntity convertFeatureDataToStudentEntity(Map<String, String> data){
        return new StudentEntity(
                null,
                data.get("studentName"),
                data.get("studentEmail"),
                Long.valueOf(data.get("classroomId")),
                data.get("registration"),
                new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
        );
    }
}
