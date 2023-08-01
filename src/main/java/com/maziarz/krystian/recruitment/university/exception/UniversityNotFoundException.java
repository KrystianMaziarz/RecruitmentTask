package com.maziarz.krystian.recruitment.university.exception;

public class UniversityNotFoundException extends RuntimeException {

  public UniversityNotFoundException(Long id) {
    super(buildErrorMessage(id));
  }

  private static String buildErrorMessage(Long id) {
    return String.format("University with id %s not found", id);
  }
}
