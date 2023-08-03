package com.maziarz.krystian.recruitment.university;

import com.maziarz.krystian.recruitment.university.dto.UniversityRequestDto;
import com.maziarz.krystian.recruitment.university.dto.UniversityResponseDto;
import java.util.Collections;
import org.springframework.http.HttpStatus;

public class UniversityDataFactory {

  public static final Long UNIVERSITY_ID = 10L;

  public static final Long WRONG_UNIVERSITY_ID = 999L;

  static final int BAD_REQUEST = HttpStatus.BAD_REQUEST.value();

  static final int OK = HttpStatus.OK.value();

  static final int NOT_FOUND = HttpStatus.NOT_FOUND.value();

  static final int CREATED = HttpStatus.CREATED.value();

  static final int NO_CONTENT = HttpStatus.NO_CONTENT.value();

  static String getJsonToAddUniversity() {
    return """
                {
                   "name":"JsonAddUniversityName",
                   "address":"JsonAddUniversityAddress"
                }
                """;
  }

  static String getJsonToAddStudentWithEmptyName() {
    return """
                {
                   "name":"",
                   "address":"JsonAddUniversityAddress"
                }
                """;
  }

  static String getJsonToAddStudentWithEmptyAddress() {
    return """
                {
                   "name":"JsonAddUniversityName",
                   "address":""
                }
                """;
  }

  static String getJsonToUpdateUniversity() {
    return """
                {
                   "name":"JsonUpdateUniversityName",
                   "address":"JsonUpdateUniversityAddress"
                }
                """;
  }

  static UniversityResponseDto getUniversityResponseDtoFromJsonAfterUpdate() {

    return new UniversityResponseDto(
        10L, "JsonUpdateUniversityName", "JsonUpdateUniversityAddress");
  }

  static UniversityResponseDto getUniversityResponseDtoFromJson() {

    return new UniversityResponseDto(1L, "JsonAddUniversityName", "JsonAddUniversityAddress");
  }

  static UniversityResponseDto getUniversityResponseDto() {

    return new UniversityResponseDto(10L, "UniversityNameTest", "UniversityAddressTest");
  }

  static UniversityRequestDto getUniversityRequestDtoForAddUniversity() {

    return new UniversityRequestDto("UniversityNameAdded", "UniversityAddressAdded");
  }

  static UniversityRequestDto getUniversityRequestDtoForUpdateUniversity() {

    return new UniversityRequestDto("UniversityNameUpdated", "UniversityAddressUpdated");
  }

  static UniversityEntity getUniversityEntity() {

    return new UniversityEntity(
        10L,
        "UniversityNameTest",
        "UniversityAddressTest",
        Collections.emptyList(),
        Collections.emptyList());
  }

  static UniversityEntity getUniversityEntityWithoutId() {

    return new UniversityEntity("UniversityNameAdded", "UniversityAddressAdded");
  }

  static UniversityResponseDto getUniversityResponseAfterUpdate() {

    return new UniversityResponseDto(10L, "UniversityNameUpdated", "UniversityAddressUpdated");
  }
}
