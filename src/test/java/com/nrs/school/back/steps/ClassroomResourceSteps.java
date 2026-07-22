package com.nrs.school.back.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.ResponseEntity;

import com.nrs.school.back.StepDefinitionsDefault;
import com.nrs.school.back.entities.ClassroomEntity;
import com.nrs.school.back.entities.StudentEntity;
import com.nrs.school.back.entities.dto.classroom.ClassroomResponse;
import com.nrs.school.back.entities.dto.student.StudentResponse;
import com.nrs.school.back.fixtures.TestApiFixtures;
import com.nrs.school.back.repository.ClassroomRepository;
import com.nrs.school.back.repository.StudentRepository;
import com.nrs.school.back.resource.ClassroomResource;

public class ClassroomResourceSteps extends StepDefinitionsDefault {

    private static final UUID TEST_CLASSROOM_REF_CODE = UUID.fromString("a1b2c3d4-e5f6-7890-abcd-ef1234567890");

    private final TestApiFixtures testApiFixtures;
    private final ClassroomRepository classroomRepository;
    private final StudentRepository studentRepository;

    private ClassroomEntity savedClassroom;
    private ResponseEntity<ClassroomResponse> classroomResponse;
    private ResponseEntity<StudentResponse> classroomStudentsResponse;

    public ClassroomResourceSteps(TestApiFixtures testApiFixtures, ClassroomRepository classroomRepository, StudentRepository studentRepository) {
        this.testApiFixtures = testApiFixtures;
        this.classroomRepository = classroomRepository;
        this.studentRepository = studentRepository;
    }

    @Given("a classroom in database")
    public void aClassroomInDatabase() {
        var classroom = new ClassroomEntity();
        classroom.setClassroomName("teste");
        classroom.setTeacher("Professor Teste");
        classroom.setCapacity(0);
        classroom.setClassNumber(0);
        classroom.setShift(0);
        classroom.setClassroomReferenceCode(TEST_CLASSROOM_REF_CODE);
        savedClassroom = classroomRepository.save(classroom);
    }

    @Given("a student in this classroom")
    public void aStudentInThisClassroom(List<Map<String, String>> studentData) {
        var data = studentData.get(0);
        var student = new StudentEntity();
        student.setStudentName(data.get("studentName"));
        student.setStudentEmail(data.get("studentEmail"));
        student.setRegistration(data.get("registration"));
        student.setStudentReferenceCode(UUID.randomUUID());
        student.setClassroomId(savedClassroom.getId());
        studentRepository.save(student);
    }

    @When("the client sends a GET http request get all classrooms")
    public void findAllClassrooms() throws URISyntaxException {
        this.classroomResponse = testApiFixtures.makeGetRequest(ClassroomResource.BASE_PATH, ClassroomResponse.class);
    }

    @When("the client sends a GET http request get classroom students")
    public void getClassroomStudents() throws URISyntaxException {
        this.classroomStudentsResponse = testApiFixtures.makeGetRequest(
                ClassroomResource.BASE_PATH + "/classroom-students/" + TEST_CLASSROOM_REF_CODE,
                StudentResponse.class);
    }

    @Then("return all classrooms in the body of the response")
    public void returnAllClassrooms(final ClassroomResponse expectedResponse) {

    }

    @Then("return all classroom students")
    public void returnAllClassroomStudents(List<Map<String, String>> expectedData) {
        assertTrue(classroomStudentsResponse.getStatusCode().is2xxSuccessful());
        var students = classroomStudentsResponse.getBody().getStudents();
        assertEquals(expectedData.size(), students.size());
        for (int i = 0; i < expectedData.size(); i++) {
            var expected = expectedData.get(i);
            var actual = students.get(i);
            assertEquals(expected.get("studentName"), actual.getStudentName());
            assertEquals(expected.get("studentEmail"), actual.getStudentEmail());
            assertEquals(expected.get("classroomName"), actual.getClassroomName());
            assertEquals(expected.get("registration"), actual.getRegistration());
        }
    }

    @DataTableType
    public ClassroomResponse convertToResponse(Map<String, String> map) throws JsonProcessingException {
        String jsonString = map.get("response");
        return new ObjectMapper().readValue(jsonString, ClassroomResponse.class);
    }
}
