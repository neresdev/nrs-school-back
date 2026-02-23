package com.nrs.school.back.config;

import com.nrs.school.back.fixtures.TestApiFixtures;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StepsConfig {

    @Bean
    public TestApiFixtures test(TestRestTemplate testRestTemplate) {
        return new TestApiFixtures(testRestTemplate);
    }
}
