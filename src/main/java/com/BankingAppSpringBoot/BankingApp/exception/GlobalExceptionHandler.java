package com.BankingAppSpringBoot.BankingApp.exception;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {


    private ResponseEntity<Map<String, Object>> buildResponse(String message, HttpStatus status, String path) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        body.put("path", path);
        return new ResponseEntity<>(body, status);
    }

    //404
    @ExceptionHandler({ResourceNotFoundException.class , UsernameNotFoundException.class})
    public ResponseEntity<Map<String,Object>> handleNotFound(Exception ex, HttpServletRequest request){
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND, request.getRequestURI());
    }


    //401 
    @ExceptionHandler({UnauthorizedException.class, BadCredentialsException.class})
    public ResponseEntity<Map<String, Object>> handleUnauthorized(Exception ex, HttpServletRequest request){
        return buildResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED, request.getRequestURI());
    }

    // 403

    @ExceptionHandler({ForbiddenException.class, AccessDeniedException.class})
    public ResponseEntity<Map<String, Object>> handleForbidden(Exception ex, HttpServletRequest request) {
        return buildResponse(ex.getMessage(), HttpStatus.FORBIDDEN, request.getRequestURI());
    }

    //400
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(BadRequestException ex, HttpServletRequest request) {
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, request.getRequestURI());
    }

    // 500 Internal Server Error (fallback)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAll(Exception ex, HttpServletRequest request) {
        return buildResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request.getRequestURI());
    }


}
