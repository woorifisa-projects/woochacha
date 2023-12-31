package com.woochacha.backend.domain.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woochacha.backend.domain.member.dto.LoginResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private String errorMessage;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        sendErrorResponse(response, errorMessage);
    }

    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        // TODO: org.springframework.web.util.NestedServletException: Request processing failed; nested exception is java.lang.IllegalStateException: getWriter() has already been called for this response 예외 해결
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        LoginResponseDto errorResponse = new LoginResponseDto(HttpStatus.UNAUTHORIZED.value(), message);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), errorResponse);
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
/*
    인증 처리 예외 핸들링
 */

