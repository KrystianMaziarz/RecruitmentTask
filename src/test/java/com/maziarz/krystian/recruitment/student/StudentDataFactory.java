package com.maziarz.krystian.recruitment.student;

import com.maziarz.krystian.recruitment.lecture.LectureEntity;
import com.maziarz.krystian.recruitment.student.dto.StudentAddLectureResponseDto;
import com.maziarz.krystian.recruitment.student.dto.StudentRequestDto;
import com.maziarz.krystian.recruitment.student.dto.StudentResponseDto;
import com.maziarz.krystian.recruitment.university.UniversityEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import org.springframework.http.HttpStatus;

public class StudentDataFactory {

  public static final Long STUDENT_ID = 10L;
  public static final Long WRONG_STUDENT_ID = 123L;
  public static final Long UNIVERSITY_ID = 10L;
  public static final Long WRONG_UNIVERSITY_ID = 101010L;
  public static final Long LECTURE_ENTITY_ID = 5L;
  static final int BAD_REQUEST = HttpStatus.BAD_REQUEST.value();

  static final int OK = HttpStatus.OK.value();

  static final int NOT_FOUND = HttpStatus.NOT_FOUND.value();

  static final int CREATED = HttpStatus.CREATED.value();

  static final int NO_CONTENT = HttpStatus.NO_CONTENT.value();

  static String getJsonToAddStudent() {
    return """
                {
                    "firstName": "Krystian",
                    "lastName": "Maziarz",
                    "gender" : "MALE",
                    "universityId" : 10
                }
                """;
  }

  static String getJsonToAddStudentWithInvalidFirstName() {
    return """
                {
                    "firstName": "2314",
                    "lastName": "Maziarz",
                    "gender" : "MALE",
                    "universityId" : 10
                }
                """;
  }

  static String getJsonToAddStudentWithIEmptyFirstName() {
    return """
                {
                    "firstName": "",
                    "lastName": "Maziarz",
                    "gender" : "MALE",
                    "universityId" : 10
                }
                """;
  }

  static String getJsonToAddStudentWithIEmptyLastName() {
    return """
                {
                    "firstName": "Krystian",
                    "lastName": "",
                    "gender" : "MALE",
                    "universityId" : 10
                }
                """;
  }

  static String getJsonToAddStudentWithInvalidLastName() {
    return """
                {
                    "firstName": "Krystian",
                    "lastName": "55555",
                    "gender" : "MALE",
                    "universityId" : 10
                }
                """;
  }

  static String getJsonToUpdateStudent() {
    return """
                {
                    "firstName": "JsonFirstNameUpdate",
                    "lastName": "JsonLastNameUpdate",
                    "gender" : "FEMALE"

                }
                """;
  }

  static StudentResponseDto getStudentResponseDtoFromJsonAfterUpdate() {

    return new StudentResponseDto(10L, "JsonFirstNameUpdate", "JsonLastNameUpdate", Gender.FEMALE);
  }

  static StudentResponseDto getStudentResponseDtoFromJson() {

    return new StudentResponseDto(1L, "Krystian", "Maziarz", Gender.MALE);
  }

  public static StudentEntity getStudentEntity() {

    return new StudentEntity(
        STUDENT_ID,
        "firstNameTest",
        "lastNameTest",
        Gender.MALE,
        getUniversity(),
        new ArrayList<>());
  }

  static StudentAddLectureResponseDto getStudentAddLectureResponseDtoForItTest() {
    return new StudentAddLectureResponseDto("firstNameTest", "lastNameTest", "LectureNameTest");
  }

  static UniversityEntity getUniversity() {
    return new UniversityEntity(UNIVERSITY_ID);
  }

  static StudentResponseDto getStudentResponseDto() {
    return new StudentResponseDto(STUDENT_ID, "firstNameTest", "lastNameTest", Gender.MALE);
  }

  static StudentRequestDto getStudentRequestDto() {
    return new StudentRequestDto("firstNameAdd", "lastNameAdd", Gender.MALE, UNIVERSITY_ID);
  }

  static StudentRequestDto getStudentRequestDtoWithWrongUniversityId() {
    return new StudentRequestDto("firstNameAdd", "lastNameAdd", Gender.MALE, WRONG_UNIVERSITY_ID);
  }

  static StudentEntity getStudentEntityWithoutId() {

    return new StudentEntity(
        "firstNameAdd", "lastNameAdd", Gender.MALE, getUniversity(), Collections.emptyList());
  }

  static StudentRequestDto getStudentRequestDtoForUpdate() {
    return new StudentRequestDto("firstNameUpdate", "lastNameUpdate", Gender.FEMALE, UNIVERSITY_ID);
  }

  static StudentResponseDto getStudentResponseDtoAfterUpdate() {
    return new StudentResponseDto(STUDENT_ID, "firstNameUpdate", "lastNameUpdate", Gender.FEMALE);
  }

  static LectureEntity getLectureEntity() {

    return new LectureEntity(
        LECTURE_ENTITY_ID,
        "LectureName",
        "Description",
        LocalDateTime.now(),
        getUniversity(),
        new ArrayList<>());
  }

  static StudentAddLectureResponseDto getStudentAddLectureResponseDto() {
    return new StudentAddLectureResponseDto("firstNameTest", "lastNameTest", "LectureName");
  }
}
