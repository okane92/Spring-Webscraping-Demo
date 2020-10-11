package com.demo.webscrapingdemo.exceptions;

import com.demo.webscrapingdemo.model.ErrorResponse;
import org.jsoup.HttpStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = HttpStatusException.class)
    protected ResponseEntity<ErrorResponse> handleHttpStatusException(ServletWebRequest request) {

       ErrorResponse errorResponse = ErrorResponse.builder()
                .message("No results found ya rocket ye!")
                .path(request.getRequest().getRequestURI())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
