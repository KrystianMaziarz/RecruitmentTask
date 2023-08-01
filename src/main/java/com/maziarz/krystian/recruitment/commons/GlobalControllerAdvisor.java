package com.maziarz.krystian.recruitment.commons;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalControllerAdvisor {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleWrongValidationException(
      MethodArgumentNotValidException ex) {
    log.error("Exception while validating data has occurred", ex);

    return new ResponseEntity<>(
        new ApiError(LocalDateTime.now(), getMessage(ex)), HttpStatus.BAD_REQUEST);
  }

  private String getMessage(MethodArgumentNotValidException ex) {
    return Optional.ofNullable(ex.getFieldError())
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .or(
            () ->
                Optional.ofNullable(ex.getGlobalError())
                    .map(DefaultMessageSourceResolvable::getDefaultMessage))
        .orElse("Validation failed");
  }
}
