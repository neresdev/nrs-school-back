package com.nrs.school.back.steps;

import com.nrs.school.back.StepDefinitionsDefault;
import com.nrs.school.back.entities.StudentEntity;
import com.nrs.school.back.exceptions.ObjectNotFoundException;
import com.nrs.school.back.repository.StudentRepository;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    private void assertFields(final StudentEntity expectedEntity, final StudentEntity actualEntity) {
        assertEquals(expectedEntity.getStudentName(), actualEntity.getStudentName());
        assertEquals(expectedEntity.getStudentEmail(), actualEntity.getStudentEmail());
        assertEquals(expectedEntity.getRegistration(), actualEntity.getRegistration());
        assertEquals(expectedEntity.getStudentReferenceCode(), actualEntity.getStudentReferenceCode());
    }

    @DataTableType
    private StudentEntity convertFeatureDataToStudentEntity(Map<String, String> data){
        return new StudentEntity(
                data.get("studentName"),
                data.get("studentEmail"),
                data.get("registration"),
                UUID.randomUUID()
        );
    }
}
