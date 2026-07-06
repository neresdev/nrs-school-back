package com.nrs.school.back.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nrs.school.back.StepDefinitionsDefault;
import com.nrs.school.back.entities.dto.classroom.ClassroomResponse;
import com.nrs.school.back.fixtures.TestApiFixtures;
import com.nrs.school.back.resource.ClassroomResource;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;
import java.util.Map;

public class ClassroomResourceSteps extends StepDefinitionsDefault {

    private final TestApiFixtures testApiFixtures;

    private ResponseEntity<ClassroomResponse> classroomResponse;

    public ClassroomResourceSteps(TestApiFixtures testApiFixtures) {
        this.testApiFixtures = testApiFixtures;
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
