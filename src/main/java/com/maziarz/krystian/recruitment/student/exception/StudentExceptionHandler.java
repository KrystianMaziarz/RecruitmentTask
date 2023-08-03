package com.maziarz.krystian.recruitment.student.exception;

import com.maziarz.krystian.recruitment.commons.ApiError;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class StudentExceptionHandler {

  @ExceptionHandler(StudentNotFoundException.class)
  public ResponseEntity<ApiError> handleNotFoundException(StudentNotFoundException ex) {
    log.error("Exception while finding student has occurred", ex);
    return new ResponseEntity<>(
        new ApiError(LocalDateTime.now(), ex.getMessage()), HttpStatus.NOT_FOUND);
  }
}
