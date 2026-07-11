package com.nrs.school.back.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nrs.school.back.StepDefinitionsDefault;
import com.nrs.school.back.entities.ClassroomEntity;
import com.nrs.school.back.entities.dto.classroom.ClassroomResponse;
import com.nrs.school.back.fixtures.TestApiFixtures;
import com.nrs.school.back.repository.ClassroomRepository;
import com.nrs.school.back.resource.ClassroomResource;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;
import java.util.Map;
import java.util.UUID;

public class ClassroomResourceSteps extends StepDefinitionsDefault {

    private static final UUID TEST_CLASSROOM_REF_CODE = UUID.fromString("a1b2c3d4-e5f6-7890-abcd-ef1234567890");

    private final TestApiFixtures testApiFixtures;
    private final ClassroomRepository classroomRepository;

    private ResponseEntity<ClassroomResponse> classroomResponse;

    public ClassroomResourceSteps(TestApiFixtures testApiFixtures, ClassroomRepository classroomRepository) {
        this.testApiFixtures = testApiFixtures;
        this.classroomRepository = classroomRepository;
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
        classroomRepository.save(classroom);
    }

    @When("the client sends a GET http request get all classrooms")
    public void findAllStudent() throws URISyntaxException {
        this.classroomResponse = testApiFixtures.makeGetRequest(ClassroomResource.BASE_PATH, ClassroomResponse.class);
    }

    @Then("return all classrooms in the body of the response")
    public void returnAllClassrooms(final ClassroomResponse expectedResponse) {

    }

    @DataTableType
    public ClassroomResponse convertToResponse(Map<String, String> map) throws JsonProcessingException {
        String jsonString = map.get("response");
        return new ObjectMapper().readValue(jsonString, ClassroomResponse.class);
    }
}
