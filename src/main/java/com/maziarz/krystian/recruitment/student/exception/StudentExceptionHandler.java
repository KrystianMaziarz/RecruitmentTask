package com.maziarz.krystian.recruitment.student.exception;

import com.maziarz.krystian.recruitment.commons.ApiError;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class StudentExceptionHandler {

  @ExceptionHandler(StudentNotFoundException.class)
  public ResponseEntity<ApiError> handleNotFoundException(StudentNotFoundException ex) {
    return new ResponseEntity<>(
        new ApiError(LocalDateTime.now(), ex.getMessage()), HttpStatus.NOT_FOUND);
  }
}
