package org.fathand.identity_service.configuration;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.fathand.identity_service.exception.ErrorCode;
import org.fathand.identity_service.exception.ErrorResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Objects;

public class JwtAuthenticatonEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorCode errorCode = ErrorCode.UNAUTHENTICATED_ERROR;

        response.setStatus(errorCode.getCode());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ErrorResponse errorResponseBody = new ErrorResponse();
        errorResponseBody.setStatusCode(errorCode.getCode());
        errorResponseBody.setMessage(errorCode.getMessage());
        errorResponseBody.setTitle(errorCode.getTitle());

        ObjectMapper objectMapper = new ObjectMapper();
        String errorJson = objectMapper.writeValueAsString(errorResponseBody);
        response.getWriter().write(errorJson);
        response.flushBuffer();
    }
}
