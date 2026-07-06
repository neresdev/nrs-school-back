package com.nrs.school.back.fixtures;

import com.nrs.school.back.entities.UserEntity;
import com.nrs.school.back.entities.dto.LoginResponse;
import com.nrs.school.back.entities.dto.LoginUserDto;
import com.nrs.school.back.entities.dto.RegisterUserDto;
import com.nrs.school.back.resource.AuthenticationResource;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.ResponseErrorHandler;

import java.net.URI;
import java.net.URISyntaxException;

public class TestApiFixtures {

    private static final String DEFAULT_EMAIL = "test@test.com";
    private static final String DEFAULT_PASSWORD = "pass123";
    private static final String DEFAULT_FULL_NAME = "test test";

    private static String token;

    private final TestRestTemplate testRestTemplate;

    public TestApiFixtures(TestRestTemplate testRestTemplate) {
        this.testRestTemplate = testRestTemplate;
        this.testRestTemplate.getRestTemplate().setErrorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) {
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse response) {
            }
        });
        this.testRestTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    public void authentication() throws URISyntaxException {
        final var user = buildDefaultUser();
        if (token == null) {
            makePostRequest(AuthenticationResource.BASE_PATH + "/signup", user, UserEntity.class, false);
        }
    }

    public void login() throws URISyntaxException {
        final var login = buildDefaultLoginUser();
        final var response = makePostRequest(AuthenticationResource.BASE_PATH + "/login", login, LoginResponse.class, false);
        assert response.getBody() != null;
        if (token == null) {
            token = response.getBody().getToken();
        }
    }

    public <T> ResponseEntity<T> makeGetRequest(String basePath, Class<T> responseType) throws URISyntaxException {
        final var headers = new HttpHeaders();
        headers.setBearerAuth(token);

        final var entity = new HttpEntity<>(null, headers);

        return testRestTemplate.exchange(new URI(basePath), HttpMethod.GET, entity, responseType);
    }

    public <T> ResponseEntity<T> makePostRequest(String path, Object body, Class<T> responseType, boolean needBearerAuth) throws URISyntaxException {
        final var headers = new HttpHeaders();
        headers.setBearerAuth(token);

        final var entity = new HttpEntity<>(body, headers);

        return testRestTemplate.postForEntity(new URI(path), needBearerAuth ? entity : body, responseType);
    }

    public <T> ResponseEntity<T> makePutRequest(String path, Object body, Class<T> responseType) throws URISyntaxException {
        final var headers = new HttpHeaders();
        headers.setBearerAuth(token);

        final var entity = new HttpEntity<>(body, headers);

        return testRestTemplate.exchange(new URI(path), HttpMethod.PUT, entity, responseType);
    }

    public <T> ResponseEntity<T> makeDeleteRequest(String path, Class<T> responseType) throws URISyntaxException {
        final var headers = new HttpHeaders();
        headers.setBearerAuth(token);

        final var entity = new HttpEntity<>(null, headers);

        return testRestTemplate.exchange(new URI(path), HttpMethod.DELETE, entity, responseType);
    }


    private RegisterUserDto buildDefaultUser() {
        final var user = new RegisterUserDto();
        user.setEmail(DEFAULT_EMAIL);
        user.setPassword(DEFAULT_PASSWORD);
        user.setFullName(DEFAULT_FULL_NAME);
        return user;
    }

    private LoginUserDto buildDefaultLoginUser() {
        final var login = new LoginUserDto();
        login.setEmail(DEFAULT_EMAIL);
        login.setPassword(DEFAULT_PASSWORD);

        return login;
    }
}
