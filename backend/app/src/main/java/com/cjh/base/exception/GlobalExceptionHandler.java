package com.cjh.base.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.cjh.common.shared.exception.UnAuthorizedApiException;
import com.cjh.common.shared.exception.UnAuthorizedPageException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(UnAuthorizedPageException.class)
    public String handleSessionExpiredPageException(UnAuthorizedPageException ex, HttpServletRequest request) {
        return "redirect:/login";
    }

    @ExceptionHandler(UnAuthorizedApiException.class)
    public ResponseEntity<String> handleSessionExpiredApiException(UnAuthorizedApiException ex) {
        return ResponseEntity.status(401).build();
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNoHandlerFoundException(NoHandlerFoundException ex, HttpServletRequest request) {
        return "redirect:/error/notfound";
    }
}
