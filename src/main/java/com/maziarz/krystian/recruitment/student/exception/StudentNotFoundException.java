package com.maziarz.krystian.recruitment.student.exception;

public class StudentNotFoundException extends RuntimeException {

  public StudentNotFoundException(Long id) {
    super(buildErrorMessage(id));
  }

  private static String buildErrorMessage(Long id) {
    return String.format("Student with id %s not found", id);
  }
}
