package com.maziarz.krystian.recruitment.lecture;

import static com.maziarz.krystian.recruitment.lecture.LectureDataFactory.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maziarz.krystian.recruitment.lecture.dto.LectureResponseDto;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = "classpath:data_for_test.sql", executionPhase = BEFORE_TEST_METHOD)
@Transactional
class LectureControllerTestIT {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @Autowired private LectureRepository lectureRepository;

  @Test
  @SneakyThrows
  void shouldGetAllLectures() {
    // given
    var expected = getLectureResponseDto();
    // when
    var response =
        mockMvc.perform(MockMvcRequestBuilders.get("/lectures")).andReturn().getResponse();

    List<LectureResponseDto> result =
        objectMapper.readValue(
            response.getContentAsString(),
            objectMapper
                .getTypeFactory()
                .constructCollectionType(List.class, LectureResponseDto.class));
    // then
    assertThat(response.getStatus()).isEqualTo(OK);
    assertThat(result.size()).isEqualTo(3);
    assertThat(result.get(0)).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  @SneakyThrows
  void shouldGetSingleLecture() {
    // given
    var expected = getLectureResponseDto();
    // when
    var response =
        mockMvc.perform(MockMvcRequestBuilders.get("/lectures/10")).andReturn().getResponse();

    var result = objectMapper.readValue(response.getContentAsString(), LectureResponseDto.class);

    // then
    assertThat(response.getStatus()).isEqualTo(OK);
    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  @SneakyThrows
  void shouldThrowExceptionWhenLectureNotExists() {
    // given // when
    var response =
        mockMvc.perform(MockMvcRequestBuilders.get("/lectures/4444")).andReturn().getResponse();
    // then
    assertThat(response.getStatus()).isEqualTo(NOT_FOUND);
    assertThat(response.getContentAsString()).contains("Lecture with id 4444 not found");
  }

  @Test
  @SneakyThrows
  void shouldDeleteLectureFromDataBase() {
    // given
    var numberOfLectureInRepositoryBeforeDeleteRequest = lectureRepository.count();
    // when
    var response =
        mockMvc.perform(MockMvcRequestBuilders.delete("/lectures/10")).andReturn().getResponse();
    var notFoundResponse =
        mockMvc.perform(MockMvcRequestBuilders.get("/lectures/10")).andReturn().getResponse();
    var numberOfLectureInRepositoryAfterDeleteRequest = lectureRepository.count();
    // then
    assertThat(response.getStatus()).isEqualTo(NO_CONTENT);
    assertThat(notFoundResponse.getStatus()).isEqualTo(NOT_FOUND);
    assertThat(numberOfLectureInRepositoryBeforeDeleteRequest)
        .isNotEqualTo(numberOfLectureInRepositoryAfterDeleteRequest);
  }

  @Test
  @SneakyThrows
  void shouldNotDeleteLectureFromDataBaseBecauseLectureNotExistsAndStatusIsNoContent() {
    // given
    var numberOfLectureInRepositoryBeforeDeleteRequest = lectureRepository.count();
    // when
    var response =
        mockMvc.perform(MockMvcRequestBuilders.delete("/lectures/1444")).andReturn().getResponse();
    var numberOfLectureInRepositoryAfterDeleteRequest = lectureRepository.count();
    // then
    assertThat(response.getStatus()).isEqualTo(NO_CONTENT);
    assertThat(numberOfLectureInRepositoryBeforeDeleteRequest)
        .isEqualTo(numberOfLectureInRepositoryAfterDeleteRequest);
  }

  @Test
  @SneakyThrows
  void shouldAddLectureToDataBase() {
    // given
    var numberOfLectureInRepositoryBeforePostRequest = lectureRepository.count();
    var requestBody = getJsonToAddLecture();
    var expected = getLectureResponseDtoFromJson();
    // when
    var response =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/lectures")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
            .andReturn()
            .getResponse();
    var result = objectMapper.readValue(response.getContentAsString(), LectureResponseDto.class);
    var numberOfLectureInRepositoryAfterPostRequest = lectureRepository.count();

    // then
    assertThat(response.getStatus()).isEqualTo(CREATED);
    assertThat(numberOfLectureInRepositoryBeforePostRequest)
        .isNotEqualTo(numberOfLectureInRepositoryAfterPostRequest);
    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  @SneakyThrows
  void shouldReturnBadRequestBecauseNameIsEmpty() {
    // given
    var requestBody = getJsonToAddLectureWithEmptyName();
    // when
    var response =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/lectures")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
            .andReturn()
            .getResponse();
    // then
    assertThat(response.getStatus()).isEqualTo(BAD_REQUEST);
    assertThat(response.getContentAsString()).contains("name is mandatory");
  }

  @Test
  @SneakyThrows
  void shouldReturnBadRequestBecauseDescriptionFIsEmpty() {
    // given
    var requestBody = getJsonToAddLectureWithEmptyDescription();
    // when
    var response =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/lectures")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
            .andReturn()
            .getResponse();
    // then
    assertThat(response.getStatus()).isEqualTo(BAD_REQUEST);
    assertThat(response.getContentAsString()).contains("description is mandatory");
  }

  @Test
  @SneakyThrows
  void shouldUpdateLecture() {
    // given
    var requestBody = getJsonToUpdateLecture();
    var expected = getLectureResponseDtoFromJsonAfterUpdate();
    // when
    var response =
        mockMvc
            .perform(
                MockMvcRequestBuilders.put("/lectures/10")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
            .andReturn()
            .getResponse();
    var result = objectMapper.readValue(response.getContentAsString(), LectureResponseDto.class);
    // then
    assertThat(response.getStatus()).isEqualTo(OK);
    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  @SneakyThrows
  void shouldNotUpdateUniversityBecauseUniversityNotExists() {
    // given
    var requestBody = getJsonToUpdateLecture();
    // when
    var response =
        mockMvc
            .perform(
                MockMvcRequestBuilders.put("/lectures/12345678")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
            .andReturn()
            .getResponse();
    // then
    assertThat(response.getStatus()).isEqualTo(NOT_FOUND);
    assertThat(response.getContentAsString()).contains("Lecture with id 12345678 not found");
  }
}
