package com.maziarz.krystian.recruitment.university.exception;

import com.maziarz.krystian.recruitment.commons.NotFoundException;

public class UniversityNotFoundException extends NotFoundException {

  public UniversityNotFoundException(Long id) {
    super(buildErrorMessage(id));
  }

  private static String buildErrorMessage(Long id) {
    return String.format("University with id %s not found", id);
  }
}
