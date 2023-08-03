package com.maziarz.krystian.recruitment.lecture;

import com.maziarz.krystian.recruitment.lecture.dto.LectureRequestDto;
import com.maziarz.krystian.recruitment.lecture.dto.LectureResponseDto;
import com.maziarz.krystian.recruitment.university.UniversityEntity;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.springframework.http.HttpStatus;

public class LectureDataFactory {

  public static final Long LECTURE_ID = 10L;
  public static final Long WRONG_ID = 9999L;
  public static final Long UNIVERSITY_ID = 10L;
  public static final Long WRONG_UNIVERSITY_ID = 123123L;
  private static final LocalDateTime LECTURE_TIME = LocalDateTime.of(2222, 12, 15, 15, 10);
  private static final LocalDateTime LECTURE_TIME_AFTER_UPDATE =
      LocalDateTime.of(2444, 10, 11, 11, 44);

  static final int BAD_REQUEST = HttpStatus.BAD_REQUEST.value();

  static final int OK = HttpStatus.OK.value();

  static final int NOT_FOUND = HttpStatus.NOT_FOUND.value();

  static final int CREATED = HttpStatus.CREATED.value();

  static final int NO_CONTENT = HttpStatus.NO_CONTENT.value();

  static String getJsonToAddLecture() {
    return """
                {
                    "name": "lectureNameTest",
                    "description": "lectureDescriptionTest",
                    "lectureTime" : "2222-12-15T15:10:00",
                    "universityId" : 10
                    }
                """;
  }

  static String getJsonToAddLectureWithEmptyName() {
    return """
                {
                    "name": "",
                    "description": "lectureDescriptionAfterUpdate",
                    "lectureTime" : "2444-10-11T11:44:00",
                    "universityId" : 10
                }
                """;
  }

  static String getJsonToAddLectureWithEmptyDescription() {
    return """
                {
                    "name": "lectureNameTest",
                    "description": "",
                    "lectureTime" : "2444-10-11T11:44:00",
                    "universityId" : 10
                }
                """;
  }

  static String getJsonToUpdateLecture() {
    return """
                {
                    "name": "lectureNameAfterUpdate",
                    "description": "lectureDescriptionAfterUpdate",
                    "lectureTime" : "2444-10-11T11:44:00",
                    "universityId" : 10
                }
                """;
  }

  static LectureResponseDto getLectureResponseDtoFromJsonAfterUpdate() {

    return new LectureResponseDto(
        10L, "lectureNameAfterUpdate", "lectureDescriptionAfterUpdate", LECTURE_TIME_AFTER_UPDATE);
  }

  static LectureResponseDto getLectureResponseDtoFromJson() {

    return new LectureResponseDto(1L, "lectureNameTest", "lectureDescriptionTest", LECTURE_TIME);
  }

  static UniversityEntity getUniversityEntity() {

    return new UniversityEntity(UNIVERSITY_ID);
  }

  static LectureEntity getLectureEntity() {

    return new LectureEntity(
        LECTURE_ID,
        "LectureNameTest",
        "LectureDescriptionTest",
        LECTURE_TIME,
        getUniversityEntity(),
        List.of());
  }

  static LectureResponseDto getLectureResponseDto() {

    return new LectureResponseDto(
        LECTURE_ID, "LectureNameTest", "LectureDescriptionTest", LECTURE_TIME);
  }

  static LectureResponseDto getLectureResponseDtoAfterUpdate() {

    return new LectureResponseDto(
        LECTURE_ID, "LectureNameUpdated", "LectureDescriptionUpdated", LECTURE_TIME_AFTER_UPDATE);
  }

  static LectureRequestDto getLectureRequestDtoForAddLecture() {
    return new LectureRequestDto(
        "LectureNameAdd", "LectureDescriptionAdd", LECTURE_TIME, UNIVERSITY_ID);
  }

  static LectureRequestDto getLectureRequestDtoForAddLectureWithWrongUniversityId() {
    return new LectureRequestDto(
        "LectureNameAdd", "LectureDescriptionAdd", LECTURE_TIME, WRONG_UNIVERSITY_ID);
  }

  static LectureRequestDto getLectureRequestDtoForUpdateLecture() {
    return new LectureRequestDto(
        "LectureNameUpdated",
        "LectureDescriptionUpdated",
        LECTURE_TIME_AFTER_UPDATE,
        UNIVERSITY_ID);
  }

  static LectureEntity getLectureEntityWithoutId() {

    return new LectureEntity(
        null,
        "LectureNameAdd",
        "LectureDescriptionAdd",
        LECTURE_TIME,
        getUniversityEntity(),
        Collections.emptyList());
  }
}
