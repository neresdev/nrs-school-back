package com.nrs.school.back;

import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.springframework.test.context.ActiveProfiles;

@Suite
@IncludeEngines("cucumber")
@SelectPackages("features")
@ActiveProfiles("test")
public class CucumberIntegrationTest {

}
