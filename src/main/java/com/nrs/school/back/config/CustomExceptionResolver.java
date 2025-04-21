package com.nrs.school.back.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nrs.school.back.exceptions.ObjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomExceptionResolver implements HandlerExceptionResolver {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ModelAndView resolveException(
            HttpServletRequest request,
            HttpServletResponse response,
            @Nullable Object exception,
            Exception ex) {

        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json");

        Map<String, Object> responseObject = new HashMap<>();
        responseObject.put("timestamp", LocalDateTime.now().toString());
        responseObject.put("status", HttpServletResponse.SC_BAD_REQUEST);
        responseObject.put("error", "Bad Request");
        responseObject.put("message", ex.getMessage());
        responseObject.put("path", request.getRequestURI());

        if (exception instanceof ObjectNotFoundException) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            responseObject.put("error", "Path not found");
            responseObject.put("status", HttpServletResponse.SC_NOT_FOUND);
        }

        try {
            String json = objectMapper.writeValueAsString(responseObject);
            response.getWriter().write(json);
        } catch (IOException ignore) {}

        return new ModelAndView();
    }

}