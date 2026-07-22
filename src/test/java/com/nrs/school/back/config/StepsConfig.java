package com.nrs.school.back.config;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nrs.school.back.fixtures.TestApiFixtures;

@Configuration
public class StepsConfig {

    @Bean
    public TestApiFixtures test(TestRestTemplate testRestTemplate) {
        return new TestApiFixtures(testRestTemplate);
    }
}
