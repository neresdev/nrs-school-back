package com.nrs.school.back.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.ResponseEntity;

import com.nrs.school.back.StepDefinitionsDefault;
import com.nrs.school.back.entities.SubjectEntity;
import com.nrs.school.back.entities.dto.subject.SubjectResponse;
import com.nrs.school.back.fixtures.TestApiFixtures;
import com.nrs.school.back.repository.SubjectRepository;
import com.nrs.school.back.resource.SubjectResource;

public class SubjectResourceSteps extends StepDefinitionsDefault {

    private final TestApiFixtures testApiFixtures;
    private final SubjectRepository subjectRepository;

    private ResponseEntity<SubjectResponse> subjectResponse;

    public SubjectResourceSteps(TestApiFixtures testApiFixtures, SubjectRepository subjectRepository) {
        this.testApiFixtures = testApiFixtures;
        this.subjectRepository = subjectRepository;
    }

    @Given("subject in database")
    public void subjectInDatabase(List<SubjectEntity> entities) {
        subjectRepository.saveAll(entities);
    }

    @When("the client sends a GET http request get all subjects")
    public void findAllSubjects() throws URISyntaxException {
        this.subjectResponse = testApiFixtures.makeGetRequest(SubjectResource.BASE_PATH, SubjectResponse.class);
    }

    @Then("return all subjects")
    public void returnAllSubjects(List<Map<String, String>> expectedData) {
        assertTrue(subjectResponse.getStatusCode().is2xxSuccessful());

        final var subjects = Objects.requireNonNull(subjectResponse.getBody()).getSubjects();
        assertEquals(expectedData.size(), subjects.size());

        for (int i = 0; i < expectedData.size(); i++) {
            final var expected = expectedData.get(i);
            final var actual = subjects.get(i);
            assertEquals(expected.get("subjectDescription"), actual.getSubjectDescription());
        }
    }

    @Then("return an empty list of subjects")
    public void returnAnEmptyListOfSubjects() {
        assertTrue(subjectResponse.getStatusCode().is2xxSuccessful());
        final var subjects = Objects.requireNonNull(subjectResponse.getBody()).getSubjects();
        assertNotNull(subjects);
        assertTrue(subjects.isEmpty());
    }

    @DataTableType
    public SubjectEntity convertToSubjectEntity(Map<String, String> map) {
        return new SubjectEntity(map.get("subjectDescription"));
    }
}
