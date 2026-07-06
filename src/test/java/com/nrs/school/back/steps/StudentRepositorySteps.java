package com.nrs.school.back.steps;

import com.nrs.school.back.StepDefinitionsDefault;
import com.nrs.school.back.entities.StudentEntity;
import com.nrs.school.back.exceptions.ObjectNotFoundException;
import com.nrs.school.back.repository.StudentRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StudentRepositorySteps extends StepDefinitionsDefault {

    private final StudentRepository studentRepository;

    public StudentRepositorySteps(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Given("student in database")
    public void insertStudent(List<StudentEntity> entities){
        studentRepository.saveAll(entities);
    }

    @And("the students were saved in the database")
    public void studentsInDatabase(List<StudentEntity> expectedEntities) {
        expectedEntities.forEach(expectedEntity -> {
            final var savedEntity = studentRepository.findByRegistration(expectedEntity.getRegistration())
                    .orElseThrow(() -> new ObjectNotFoundException("Student with registration not found"));
            assertFields(expectedEntity, savedEntity);
        });
    }

    @And("there must be no students with registration {string} in database")
    public void studentWithRegistrationNotExistsInDatabase(final String registration) {
        assertTrue(studentRepository.findByRegistration(registration).isEmpty());
    }

    private void assertFields(final StudentEntity expectedEntity, final StudentEntity actualEntity) {
        assertEquals(expectedEntity.getStudentName(), actualEntity.getStudentName());
        assertEquals(expectedEntity.getStudentEmail(), actualEntity.getStudentEmail());
        assertEquals(expectedEntity.getRegistration(), actualEntity.getRegistration());
    }

    @DataTableType
    public StudentEntity convertToEntity(Map<String, String> map) {
        return new StudentEntity(map.get("studentName"), map.get("studentEmail"), map.get("registration"));
    }
}
