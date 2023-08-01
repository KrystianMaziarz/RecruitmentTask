package com.maziarz.krystian.recruitment.student;

import static com.maziarz.krystian.recruitment.student.StudentDataFactory.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maziarz.krystian.recruitment.student.dto.StudentAddLectureResponseDto;
import com.maziarz.krystian.recruitment.student.dto.StudentResponseDto;
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
class StudentControllerTestIT {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @Autowired private StudentRepository studentRepository;

  @Test
  @SneakyThrows
  void shouldGetALlStudents() {
    // given
    var expected = getStudentResponseDto();
    // when
    var response =
        mockMvc.perform(MockMvcRequestBuilders.get("/students")).andReturn().getResponse();
    List<StudentResponseDto> result =
        objectMapper.readValue(
            response.getContentAsString(),
            objectMapper
                .getTypeFactory()
                .constructCollectionType(List.class, StudentResponseDto.class));
    // then
    assertThat(response.getStatus()).isEqualTo(OK);
    assertThat(result.size()).isEqualTo(3);
    assertThat(result.get(0)).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  @SneakyThrows
  void shouldGetSingleStudent() {
    // given
    var expected = getStudentResponseDto();
    // when
    var response =
        mockMvc.perform(MockMvcRequestBuilders.get("/students/10")).andReturn().getResponse();

    var result = objectMapper.readValue(response.getContentAsString(), StudentResponseDto.class);

    // then
    assertThat(response.getStatus()).isEqualTo(OK);
    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  @SneakyThrows
  void shouldThrowExceptionWhenStudentNotExists() {
    // given // when
    var response =
        mockMvc.perform(MockMvcRequestBuilders.get("/students/999")).andReturn().getResponse();
    // then
    assertThat(response.getStatus()).isEqualTo(NOT_FOUND);
    assertThat(response.getContentAsString()).contains("Student with id 999 not found");
  }

  @Test
  @SneakyThrows
  void shouldDeleteStudentFromDataBase() {
    // given
    var numberOfStudentInRepositoryBeforeDeleteRequest = studentRepository.count();
    // when
    var response =
        mockMvc.perform(MockMvcRequestBuilders.delete("/students/10")).andReturn().getResponse();
    var notFoundResponse =
        mockMvc.perform(MockMvcRequestBuilders.get("/students/10")).andReturn().getResponse();
    var numberOfStudentInRepositoryAfterDeleteRequest = studentRepository.count();
    // then
    assertThat(response.getStatus()).isEqualTo(NO_CONTENT);
    assertThat(notFoundResponse.getStatus()).isEqualTo(NOT_FOUND);
    assertThat(numberOfStudentInRepositoryBeforeDeleteRequest)
        .isNotEqualTo(numberOfStudentInRepositoryAfterDeleteRequest);
  }

  @Test
  @SneakyThrows
  void shouldNotDeleteStudentFromDataBaseBecauseStudentNotExistsAndStatusIsNoContent() {
    // given
    var numberOfStudentInRepositoryBeforeDeleteRequest = studentRepository.count();
    // when
    var response =
        mockMvc.perform(MockMvcRequestBuilders.delete("/students/1444")).andReturn().getResponse();
    var numberOfStudentInRepositoryAfterDeleteRequest = studentRepository.count();
    // then
    assertThat(response.getStatus()).isEqualTo(NO_CONTENT);
    assertThat(numberOfStudentInRepositoryBeforeDeleteRequest)
        .isEqualTo(numberOfStudentInRepositoryAfterDeleteRequest);
  }

  @Test
  @SneakyThrows
  void shouldAddStudentToDataBase() {
    // given
    var numberOfUniversityInRepositoryBeforePostRequest = studentRepository.count();
    var requestBody = getJsonToAddStudent();
    var expected = getStudentResponseDtoFromJson();
    // when
    var response =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/students")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
            .andReturn()
            .getResponse();
    var result = objectMapper.readValue(response.getContentAsString(), StudentResponseDto.class);
    var numberOfUniversityInRepositoryAfterPostRequest = studentRepository.count();

    // then
    assertThat(response.getStatus()).isEqualTo(CREATED);
    assertThat(numberOfUniversityInRepositoryBeforePostRequest)
        .isNotEqualTo(numberOfUniversityInRepositoryAfterPostRequest);
    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  @SneakyThrows
  void shouldReturnBadRequestBecauseFirstNameIsInvalid() {
    // given
    var requestBody = getJsonToAddStudentWithInvalidFirstName();
    // when
    var response =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/students")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
            .andReturn()
            .getResponse();
    // then
    assertThat(response.getStatus()).isEqualTo(BAD_REQUEST);
    assertThat(response.getContentAsString())
        .contains("Invalid First Name format, only Polish letters are valid for First Name");
  }

  @Test
  @SneakyThrows
  void shouldReturnBadRequestBecauseFirstNameIsEmpty() {
    // given
    var requestBody = getJsonToAddStudentWithIEmptyFirstName();
    // when
    var response =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/students")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
            .andReturn()
            .getResponse();
    // then
    assertThat(response.getStatus()).isEqualTo(BAD_REQUEST);
    assertThat(response.getContentAsString()).contains("First name is mandatory");
  }

  @Test
  @SneakyThrows
  void shouldReturnBadRequestBecauseLastNameIsEmpty() {
    // given
    var requestBody = getJsonToAddStudentWithIEmptyLastName();
    // when
    var response =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/students")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
            .andReturn()
            .getResponse();
    // then
    assertThat(response.getStatus()).isEqualTo(BAD_REQUEST);
    assertThat(response.getContentAsString()).contains("Last name is mandatory");
  }

  @Test
  @SneakyThrows
  void shouldReturnBadRequestBecauseLastNameIsInvalid() {
    // given
    var requestBody = getJsonToAddStudentWithInvalidLastName();
    // when
    var response =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/students")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
            .andReturn()
            .getResponse();
    // then
    assertThat(response.getStatus()).isEqualTo(BAD_REQUEST);
    assertThat(response.getContentAsString())
        .contains("Invalid Last Name format, only Polish letters are valid for Last Name.");
  }

  @Test
  @SneakyThrows
  void shouldUpdateStudent() {
    // given
    var requestBody = getJsonToUpdateStudent();
    var expected = getStudentResponseDtoFromJsonAfterUpdate();
    // when
    var response =
        mockMvc
            .perform(
                MockMvcRequestBuilders.put("/students/10")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
            .andReturn()
            .getResponse();
    var result = objectMapper.readValue(response.getContentAsString(), StudentResponseDto.class);
    // then
    assertThat(response.getStatus()).isEqualTo(OK);
    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  @SneakyThrows
  void shouldNotUpdateUniversityBecauseUniversityNotExists() {
    // given
    var requestBody = getJsonToUpdateStudent();
    // when
    var response =
        mockMvc
            .perform(
                MockMvcRequestBuilders.put("/students/123123")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
            .andReturn()
            .getResponse();
    // then
    assertThat(response.getStatus()).isEqualTo(NOT_FOUND);
    assertThat(response.getContentAsString()).contains("Student with id 123123 not found");
  }

  @Test
  @SneakyThrows
  void shouldAddLectureToStudent() {
    // given
    var expected = getStudentAddLectureResponseDtoForItTest();
    // when
    var response =
        mockMvc
            .perform(MockMvcRequestBuilders.put("/students/addLecture/10?lectureId=10"))
            .andReturn()
            .getResponse();
    var result =
        objectMapper.readValue(response.getContentAsString(), StudentAddLectureResponseDto.class);
    // then
    assertThat(response.getStatus()).isEqualTo(OK);
    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
  }
}
