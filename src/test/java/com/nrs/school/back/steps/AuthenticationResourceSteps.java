package com.nrs.school.back.steps;

import com.nrs.school.back.StepDefinitionsDefault;
import com.nrs.school.back.entities.UserEntity;
import com.nrs.school.back.entities.dto.LoginResponse;
import com.nrs.school.back.entities.dto.LoginUserDto;
import com.nrs.school.back.entities.dto.RegisterUserDto;
import com.nrs.school.back.fixtures.TestApiFixtures;
import com.nrs.school.back.repository.UserRepository;
import com.nrs.school.back.resource.AuthenticationResource;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationResourceSteps extends StepDefinitionsDefault {

    private final TestApiFixtures testApiFixtures;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private ResponseEntity<UserEntity> registerResponse;
    private ResponseEntity<LoginResponse> loginResponse;

    public AuthenticationResourceSteps(TestApiFixtures testApiFixtures, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.testApiFixtures = testApiFixtures;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Given("a user in database")
    public void aUserInDatabase(List<Map<String, String>> userData) {
        final var user = convertToRegisterUserDto(userData.get(0));
        final var userEntity = new UserEntity();
        userEntity.setFullName(user.getFullName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(userEntity);
    }

    @When("the client sends a POST http request to register a new user")
    public void registerUser(List<Map<String, String>> userData) throws Exception {
        final var userDto = convertToRegisterUserDto(userData.get(0));
        registerResponse = testApiFixtures.makePostRequest(
            AuthenticationResource.BASE_PATH + "/signup",
            userDto,
            UserEntity.class,
            false
        );
    }

    @When("the client sends a POST http request to login")
    public void loginUser(List<Map<String, String>> loginData) throws Exception {
        final var loginDto = convertToLoginUserDto(loginData.get(0));
        loginResponse = testApiFixtures.makePostRequest(
            AuthenticationResource.BASE_PATH + "/login",
            loginDto,
            LoginResponse.class,
            false
        );
    }

    @Then("return a auth response with status code {int}")
    public void returnStatusCode(int statusCode) {
        if (registerResponse != null) {
            assertEquals(HttpStatus.valueOf(statusCode), registerResponse.getStatusCode());
        } else if (loginResponse != null) {
            assertEquals(HttpStatus.valueOf(statusCode), loginResponse.getStatusCode());
        }
    }

    @Then("the user was saved in the database")
    public void userSavedInDatabase(List<Map<String, String>> expectedData) {
        final var expectedUser = expectedData.get(0);
        final var actualUser = userRepository.findByEmail(expectedUser.get("email"))
            .orElseThrow(() -> new AssertionError("User not found in database"));

        assertEquals(expectedUser.get("fullName"), actualUser.getFullName());
        assertEquals(expectedUser.get("email"), actualUser.getEmail());
    }

    @Then("return a jwt token in the response body")
    public void returnJwtToken() {
        assertNotNull(loginResponse.getBody());
        assertNotNull(loginResponse.getBody().getToken());
        assertTrue(loginResponse.getBody().getToken().length() > 0);
        assertTrue(loginResponse.getBody().getExpiresIn() > 0);
    }

    @DataTableType
    public RegisterUserDto convertToRegisterUserDto(Map<String, String> map) {
        final var dto = new RegisterUserDto();
        dto.setFullName(map.get("fullName"));
        dto.setEmail(map.get("email"));
        dto.setPassword(map.get("password"));
        return dto;
    }

    @DataTableType
    public LoginUserDto convertToLoginUserDto(Map<String, String> map) {
        final var dto = new LoginUserDto();
        dto.setEmail(map.get("email"));
        dto.setPassword(map.get("password"));
        return dto;
    }
}
