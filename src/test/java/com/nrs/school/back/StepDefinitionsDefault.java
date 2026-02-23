package com.nrs.school.back;

import com.nrs.school.back.fixtures.TestApiFixtures;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = Application.class)
@ActiveProfiles("test")
public class StepDefinitionsApiDefault<T> {
    private final TestApiFixtures testApiFixtures;

    public StepDefinitionsApiDefault(TestApiFixtures testApiFixtures) {
        this.testApiFixtures = testApiFixtures;
    }
}
