package com.maziarz.krystian.recruitment.lecture.exception;

import com.maziarz.krystian.recruitment.commons.ApiError;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class LectureExceptionHandler {

  @ExceptionHandler(LectureNotFoundException.class)
  public ResponseEntity<ApiError> handleNotFoundException(LectureNotFoundException ex) {
    log.error("Exception while finding lecture has occurred", ex);
    return new ResponseEntity<>(
        new ApiError(LocalDateTime.now(), ex.getMessage()), HttpStatus.NOT_FOUND);
  }
}
