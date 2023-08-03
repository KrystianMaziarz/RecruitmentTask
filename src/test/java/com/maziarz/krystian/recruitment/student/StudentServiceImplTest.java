package com.maziarz.krystian.recruitment.student;

import static com.maziarz.krystian.recruitment.student.StudentDataFactory.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.*;

import com.maziarz.krystian.recruitment.lecture.LectureService;
import com.maziarz.krystian.recruitment.lecture.LectureServiceImpl;
import com.maziarz.krystian.recruitment.student.dto.StudentResponseDto;
import com.maziarz.krystian.recruitment.student.exception.StudentNotFoundException;
import com.maziarz.krystian.recruitment.university.UniversityService;
import com.maziarz.krystian.recruitment.university.UniversityServiceImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;

class StudentServiceImplTest {

  private final StudentRepository studentRepository = mock(StudentRepository.class);
  private final UniversityService universityService = mock(UniversityServiceImpl.class);
  private final LectureService lectureService = mock(LectureServiceImpl.class);
  private final StudentMapper studentMapper = Mappers.getMapper(StudentMapper.class);
  private final StudentService studentService =
      new StudentServiceImpl(studentRepository, studentMapper, universityService, lectureService);

  @Test
  void getListOfAllStudents() {
    // given
    var listOfStudents = List.of(getStudentEntity());
    var expected = getStudentResponseDto();
    when(studentRepository.findAll()).thenReturn(listOfStudents);
    // when
    var result = studentService.getAllStudents();
    // then
    assertThat(result.size()).isOne();
    assertThat(result.get(0)).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void shouldFindStudentById() {
    // given
    var optionalStudent = Optional.of(getStudentEntity());
    var expected = getStudentResponseDto();
    when(studentRepository.findById(STUDENT_ID)).thenReturn(optionalStudent);
    // when
    StudentResponseDto result = studentService.findById(STUDENT_ID);
    // then
    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void shouldThrowStudentNotFoundException() {
    // given//when
    when(studentRepository.findById(WRONG_STUDENT_ID)).thenReturn(Optional.empty());
    // then
    assertThrowsExactly(
        StudentNotFoundException.class, () -> studentService.findById(WRONG_STUDENT_ID));
  }

  @Test
  void shouldDeleteStudentById() {
    // given //when
    studentService.deleteById(STUDENT_ID);
    // then
    verify(studentRepository).deleteById(STUDENT_ID);
  }

  @Test
  void shouldAddNewStudent() {
    // given
    var argumentCaptor = ArgumentCaptor.forClass(StudentEntity.class);
    var requestDtoForAddStudent = getStudentRequestDto();
    var expected = getStudentEntityWithoutId();
    doNothing().when(universityService).checkIfUniversityExists(UNIVERSITY_ID);
    when(studentRepository.save(any(StudentEntity.class))).thenReturn(expected);
    // when
    studentService.addStudent(requestDtoForAddStudent);
    // then
    verify(studentRepository).save(argumentCaptor.capture());
    var result = argumentCaptor.getValue();
    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void shouldUpdateStudent() {
    // given
    var optionalStudent = Optional.of(getStudentEntity());
    var requestStudentForUpdate = getStudentRequestDtoForUpdate();
    var expected = getStudentResponseDtoAfterUpdate();
    when(studentRepository.findById(STUDENT_ID)).thenReturn(optionalStudent);
    // when
    StudentResponseDto result = studentService.updateStudent(STUDENT_ID, requestStudentForUpdate);
    // then
    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void shouldThrowStudentNotFoundExceptionWhenTryingToUpdateNotExistingStudent() {
    // given
    var studentUpdateRequestDto = getStudentRequestDtoForUpdate();
    when(studentRepository.findById(WRONG_STUDENT_ID)).thenReturn(Optional.empty());
    // when//then
    assertThrowsExactly(
        StudentNotFoundException.class,
        () -> studentService.updateStudent(WRONG_STUDENT_ID, studentUpdateRequestDto));
  }

  @Test
  void shouldAddLectureForStudent() {
    // given
    var optionalStudentEntity = Optional.of(getStudentEntity());
    var lectureEntity = getLectureEntity();
    var expected = getStudentAddLectureResponseDto();
    when(lectureService.checkIfLectureExists(LECTURE_ENTITY_ID)).thenReturn(lectureEntity);
    when(studentRepository.findById(STUDENT_ID)).thenReturn(optionalStudentEntity);
    // when
    var result = studentService.addLectureToStudent(STUDENT_ID, LECTURE_ENTITY_ID);
    // then
    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
  }
}
