package com.nrs.school.back.steps;

import com.nrs.school.back.StepDefinitionsDefault;
import com.nrs.school.back.entities.ClassroomEntity;
import com.nrs.school.back.entities.StudentEntity;
import com.nrs.school.back.exceptions.ObjectNotFoundException;
import com.nrs.school.back.repository.ClassroomRepository;
import com.nrs.school.back.repository.StudentRepository;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StudentRepositorySteps extends StepDefinitionsDefault {

    private final StudentRepository studentRepository;
    private final ClassroomRepository classroomRepository;

    public StudentRepositorySteps(StudentRepository studentRepository, ClassroomRepository classroomRepository) {
        this.studentRepository = studentRepository;
        this.classroomRepository = classroomRepository;
    }

    @Given("student in database")
    public void insertStudent(List<StudentEntity> entities){
        studentRepository.saveAll(entities);
    }

    @Given("a classroom")
    public void insertClassroom(List<ClassroomEntity> entities) {
        classroomRepository.saveAll(entities);
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
        var entity = new StudentEntity(map.get("studentName"), map.get("studentEmail"), map.get("registration"));
        var classroomName = map.get("classroomName");
        if (classroomName != null) {
            classroomRepository.findByClassroomName(classroomName)
                    .ifPresent(c -> entity.setClassroomId(c.getId()));
        }
        return entity;
    }

    @DataTableType
    public ClassroomEntity convertToClassroomEntity(Map<String, String> map) {
        var classroom = new ClassroomEntity();
        classroom.setClassroomName(map.get("classroomName"));
        classroom.setTeacher(map.get("teacher"));
        if (map.containsKey("capacity")) {
            classroom.setCapacity(Integer.parseInt(map.get("capacity")));
        }
        if (map.containsKey("classNumber")) {
            classroom.setClassNumber(Integer.parseInt(map.get("classNumber")));
        }
        if (map.containsKey("shift")) {
            classroom.setShift(Integer.parseInt(map.get("shift")));
        }
        classroom.setClassroomReferenceCode(UUID.randomUUID());
        return classroom;
    }
}
