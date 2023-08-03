package com.maziarz.krystian.recruitment.lecture.exception;

import com.maziarz.krystian.recruitment.commons.NotFoundException;

public class LectureNotFoundException extends NotFoundException {

  public LectureNotFoundException(Long id) {
    super(buildErrorMessage(id));
  }

  private static String buildErrorMessage(Long id) {
    return String.format("Lecture with id %s not found", id);
  }
}
