package com.nrs.school.back.steps;

import java.net.URISyntaxException;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;

import com.nrs.school.back.StepDefinitionsDefault;
import com.nrs.school.back.fixtures.TestApiFixtures;

public class AuthSteps extends StepDefinitionsDefault {

    private final TestApiFixtures testApiFixtures;

    public AuthSteps(TestApiFixtures testApiFixtures) {
        this.testApiFixtures = testApiFixtures;
    }

    @Given("authentication was performed")
    public void authentication() throws URISyntaxException {
        testApiFixtures.authentication();
    }

    @And("login was performed")
    public void login() throws URISyntaxException {
        testApiFixtures.login();
    }
}
