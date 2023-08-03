package com.maziarz.krystian.recruitment.university;

import static com.maziarz.krystian.recruitment.university.UniversityDataFactory.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maziarz.krystian.recruitment.university.dto.UniversityResponseDto;
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
class UniversityControllerTestIT {
  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @Autowired private UniversityRepository universityRepository;

  @Test
  @SneakyThrows
  void shouldGetAllUniversities() {
    // given
    var expected = getUniversityResponseDto();
    // when
    var response =
        mockMvc.perform(MockMvcRequestBuilders.get("/universities")).andReturn().getResponse();

    List<UniversityResponseDto> result =
        objectMapper.readValue(
            response.getContentAsString(),
            objectMapper
                .getTypeFactory()
                .constructCollectionType(List.class, UniversityResponseDto.class));
    // then
    assertThat(response.getStatus()).isEqualTo(OK);
    assertThat(result.size()).isEqualTo(3);
    assertThat(result.get(0)).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  @SneakyThrows
  void shouldGetSingleUniversity() {
    // given
    var expected = getUniversityResponseDto();
    // when
    var response =
        mockMvc.perform(MockMvcRequestBuilders.get("/universities/10")).andReturn().getResponse();

    var result = objectMapper.readValue(response.getContentAsString(), UniversityResponseDto.class);

    // then
    assertThat(response.getStatus()).isEqualTo(OK);
    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  @SneakyThrows
  void shouldThrowExceptionWhenUniversityNotExists() {
    // given // when
    var response =
        mockMvc.perform(MockMvcRequestBuilders.get("/universities/999")).andReturn().getResponse();
    // then
    assertThat(response.getStatus()).isEqualTo(NOT_FOUND);
    assertThat(response.getContentAsString()).contains("University with id 999 not found");
  }

  @Test
  @SneakyThrows
  void shouldDeleteUniversityFromDataBase() {
    // given
    var numberOfUniversityInRepositoryBeforeDeleteRequest = universityRepository.count();
    // when
    var response =
        mockMvc
            .perform(MockMvcRequestBuilders.delete("/universities/10"))
            .andReturn()
            .getResponse();
    var notFoundResponse =
        mockMvc.perform(MockMvcRequestBuilders.get("/universities/10")).andReturn().getResponse();
    var numberOfUniversityInRepositoryAfterDeleteRequest = universityRepository.count();
    // then
    assertThat(response.getStatus()).isEqualTo(NO_CONTENT);
    assertThat(notFoundResponse.getStatus()).isEqualTo(NOT_FOUND);
    assertThat(numberOfUniversityInRepositoryBeforeDeleteRequest)
        .isNotEqualTo(numberOfUniversityInRepositoryAfterDeleteRequest);
  }

  @Test
  @SneakyThrows
  void shouldNotDeleteUniversityFromDataBaseBecauseUniversityNotExistsAndStatusIsNoContent() {
    // given
    var numberOfUniversityInRepositoryBeforeDeleteRequest = universityRepository.count();
    // when
    var response =
        mockMvc
            .perform(MockMvcRequestBuilders.delete("/universities/1444"))
            .andReturn()
            .getResponse();
    var numberOfUniversityInRepositoryAfterDeleteRequest = universityRepository.count();
    // then
    assertThat(response.getStatus()).isEqualTo(NO_CONTENT);
    assertThat(numberOfUniversityInRepositoryBeforeDeleteRequest)
        .isEqualTo(numberOfUniversityInRepositoryAfterDeleteRequest);
  }

  @Test
  @SneakyThrows
  void shouldAddUniversityToDataBase() {
    // given
    var numberOfUniversityInRepositoryBeforePostRequest = universityRepository.count();
    var requestBody = getJsonToAddUniversity();
    var expected = getUniversityResponseDtoFromJson();
    // when
    var response =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/universities")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
            .andReturn()
            .getResponse();
    var result = objectMapper.readValue(response.getContentAsString(), UniversityResponseDto.class);
    var numberOfUniversityInRepositoryAfterPostRequest = universityRepository.count();

    // then
    assertThat(response.getStatus()).isEqualTo(CREATED);
    assertThat(numberOfUniversityInRepositoryBeforePostRequest)
        .isNotEqualTo(numberOfUniversityInRepositoryAfterPostRequest);
    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  @SneakyThrows
  void shouldReturnBadRequestBecauseFirstNameIsEmpty() {
    // given
    var requestBody = getJsonToAddStudentWithEmptyName();
    // when
    var response =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/universities")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
            .andReturn()
            .getResponse();
    // then
    assertThat(response.getStatus()).isEqualTo(BAD_REQUEST);
    assertThat(response.getContentAsString()).contains("Name is mandatory");
  }

  @Test
  @SneakyThrows
  void shouldReturnBadRequestBecauseFirstAddressIsEmpty() {
    // given
    var requestBody = getJsonToAddStudentWithEmptyAddress();
    // when
    var response =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/universities")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
            .andReturn()
            .getResponse();
    // then
    assertThat(response.getStatus()).isEqualTo(BAD_REQUEST);
    assertThat(response.getContentAsString()).contains("Address is mandatory");
  }

  @Test
  @SneakyThrows
  void shouldUpdateUniversity() {
    // given
    var requestBody = getJsonToUpdateUniversity();
    var expected = getUniversityResponseDtoFromJsonAfterUpdate();
    // when
    var response =
        mockMvc
            .perform(
                MockMvcRequestBuilders.put("/universities/10")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
            .andReturn()
            .getResponse();
    var result = objectMapper.readValue(response.getContentAsString(), UniversityResponseDto.class);
    // then
    assertThat(response.getStatus()).isEqualTo(OK);
    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  @SneakyThrows
  void shouldNotUpdateUniversityBecauseUniversityNotExists() {
    // given
    var requestBody = getJsonToUpdateUniversity();
    // when
    var response =
        mockMvc
            .perform(
                MockMvcRequestBuilders.put("/universities/141414")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
            .andReturn()
            .getResponse();
    // then
    assertThat(response.getStatus()).isEqualTo(NOT_FOUND);
    assertThat(response.getContentAsString()).contains("University with id 141414 not found");
  }
}
