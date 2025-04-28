package com.nrs.school.back.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nrs.school.back.exceptions.MissingAuthorizationException;
import com.nrs.school.back.exceptions.ObjectNotFoundException;
import com.nrs.school.back.exceptions.StudentClassroomNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.Nullable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Component
public class CustomExceptionResolver implements HandlerExceptionResolver {
    public static final Logger LOGGER = Logger.getLogger(CustomExceptionResolver.class.getName());

    private static final String DEFAULT_ERROR_MESSAGE = "Unauthorized";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ModelAndView resolveException(
            HttpServletRequest request,
            HttpServletResponse response,
            @Nullable Object handler,
            Exception exception) {

        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json");

        Map<String, Object> responseObject = new HashMap<>();
        responseObject.put("timestamp", LocalDateTime.now().toString());
        responseObject.put("message", exception.getMessage());
        responseObject.put("path", request.getRequestURI());

        handleException(exception, response, responseObject);

        try {
            String json = objectMapper.writeValueAsString(responseObject);
            response.getWriter().write(json);
        } catch (IOException ignore) {}

        return new ModelAndView();
    }

    private void handleException(Exception exception, HttpServletResponse response, Map<String, Object> responseObject) {
        LOGGER.info("Request error: " + exception.getMessage());

        if (exception instanceof ObjectNotFoundException) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            responseObject.put("error", "Path not found");
            responseObject.put("status", HttpServletResponse.SC_NOT_FOUND);
        } else if (exception instanceof AccountStatusException) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            responseObject.put("error", "The account is locked");
            responseObject.put("status", HttpServletResponse.SC_FORBIDDEN);
        } else if (exception instanceof AccessDeniedException) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            responseObject.put("error", "You are not authorized to access this resource");
            responseObject.put("status", HttpServletResponse.SC_FORBIDDEN);
        } else if (exception instanceof SignatureException) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            responseObject.put("error", "The JWT signature is invalid");
            responseObject.put("status", HttpServletResponse.SC_FORBIDDEN);
        } else if (exception instanceof ExpiredJwtException) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            responseObject.put("error", "The JWT token has expireda");
            responseObject.put("status", HttpServletResponse.SC_FORBIDDEN);
        } else if (exception instanceof MissingAuthorizationException) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            responseObject.put("error", exception.getMessage());
            responseObject.put("status", HttpServletResponse.SC_FORBIDDEN);
        } else if (exception instanceof StudentClassroomNotFoundException) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseObject.put("error",  exception.getMessage());
            responseObject.put("status", HttpServletResponse.SC_BAD_REQUEST);
            responseObject.put("errorCode", ((StudentClassroomNotFoundException) exception).getCode());
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            responseObject.put("error", DEFAULT_ERROR_MESSAGE);
            responseObject.put("status", HttpServletResponse.SC_UNAUTHORIZED);
            responseObject.put("message", DEFAULT_ERROR_MESSAGE);
        }
    }

}