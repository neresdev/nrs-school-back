package com.nrs.school.back;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@CucumberContextConfiguration
@RunWith(Cucumber.class)
@CucumberOptions(features="src/test/resources/features")
public class JobsApplicationTest{
}
