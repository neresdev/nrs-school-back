package com.nrs.school.back.steps;

import com.nrs.school.back.StepDefinitionsDefault;
import com.nrs.school.back.fixtures.TestApiFixtures;
import io.cucumber.java.en.Given;

import java.net.URISyntaxException;

public class ApiSteps extends StepDefinitionsDefault {

    private final TestApiFixtures testApiFixtures;

    public ApiSteps(TestApiFixtures testApiFixtures) {
        this.testApiFixtures = testApiFixtures;
    }

    @Given("authentication was performed")
    public void authentication() throws URISyntaxException {
        testApiFixtures.authentication();
    }
}
