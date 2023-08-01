package com.maziarz.krystian.recruitment.university.exception;

import com.maziarz.krystian.recruitment.commons.ApiError;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UniversityExceptionHandler {

  @ExceptionHandler(UniversityNotFoundException.class)
  public ResponseEntity<ApiError> handleNotFoundException(UniversityNotFoundException ex) {
    return new ResponseEntity<>(
        new ApiError(LocalDateTime.now(), ex.getMessage()), HttpStatus.NOT_FOUND);
  }
}
