package com.maziarz.krystian.recruitment.student.exception;

import com.maziarz.krystian.recruitment.commons.NotFoundException;

public class StudentNotFoundException extends NotFoundException {

  public StudentNotFoundException(Long id) {
    super(buildErrorMessage(id));
  }

  private static String buildErrorMessage(Long id) {
    return String.format("Student with id %s not found", id);
  }
}
