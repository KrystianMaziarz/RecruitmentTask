package com.maziarz.krystian.recruitment.lecture.exception;

public class LectureNotFoundException extends RuntimeException {

  public LectureNotFoundException(Long id) {
    super(buildErrorMessage(id));
  }

  private static String buildErrorMessage(Long id) {
    return String.format("Lecture with id %s not found", id);
  }
}
