package com.maziarz.krystian.recruitment.lecture;

import static com.maziarz.krystian.recruitment.lecture.LectureDataFactory.*;
import static com.maziarz.krystian.recruitment.student.StudentDataFactory.UNIVERSITY_ID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.*;

import com.maziarz.krystian.recruitment.lecture.exception.LectureNotFoundException;
import com.maziarz.krystian.recruitment.university.UniversityService;
import com.maziarz.krystian.recruitment.university.UniversityServiceImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;

class LectureServiceImplTest {

  private final LectureRepository lectureRepository = mock(LectureRepository.class);

  private final UniversityService universityService = mock(UniversityServiceImpl.class);

  private final LectureMapper lectureMapper = Mappers.getMapper(LectureMapper.class);

  private final LectureService lectureService =
      new LectureServiceImpl(lectureRepository, universityService, lectureMapper);

  @Test
  void shouldReturnListOfAllLectures() {
    // given
    var lecturesList = List.of(getLectureEntity());
    var expected = getLectureResponseDto();
    when(lectureRepository.findAll()).thenReturn(lecturesList);
    // when
    var result = lectureService.getAllLectures();
    // the
    assertThat(result.size()).isOne();
    assertThat(result.get(0)).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void shouldFindLectureById() {
    // given
    var expected = getLectureResponseDto();
    var optionalLecture = Optional.of(getLectureEntity());
    when(lectureRepository.findById(LECTURE_ID)).thenReturn(optionalLecture);
    // when
    var result = lectureService.findById(LECTURE_ID);
    // then
    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void shouldThrowLectureNotFoundExceptionWhenLectureNotExists() {
    // given
    when(lectureRepository.findById(WRONG_ID)).thenReturn(Optional.empty());
    // when //then
    assertThrowsExactly(LectureNotFoundException.class, () -> lectureService.findById(WRONG_ID));
  }

  @Test
  void shouldDeleteLectureById() {
    // given //when
    lectureService.deleteById(LECTURE_ID);
    // then
    verify(lectureRepository).deleteById(LECTURE_ID);
  }

  @Test
  void shouldAddNewLecture() {
    // given
    var argumentCaptor = ArgumentCaptor.forClass(LectureEntity.class);
    var requestDtoForAddLecture = getLectureRequestDtoForAddLecture();
    var expected = getLectureEntityWithoutId();
    doNothing().when(universityService).checkIfUniversityExists(UNIVERSITY_ID);
    // when
    lectureService.addLecture(requestDtoForAddLecture);
    // then
    verify(lectureRepository).save(argumentCaptor.capture());
    var result = argumentCaptor.getValue();

    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void shouldUpdateLecture() {
    var optionalLectureEntity = Optional.of(getLectureEntity());
    var requestLectureForUpdate = getLectureRequestDtoForUpdateLecture();
    var expected = getLectureResponseDtoAfterUpdate();
    when(lectureRepository.findById(LECTURE_ID)).thenReturn(optionalLectureEntity);
    // when
    var result = lectureService.updateLecture(LECTURE_ID, requestLectureForUpdate);
    // then
    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void shouldThrowLectureNotFoundExceptionWhenTryingToUpdateNotExistingLecture() {
    // given
    var updateRequestDto = getLectureRequestDtoForUpdateLecture();
    when(lectureRepository.findById(WRONG_ID)).thenReturn(Optional.empty());
    // when//then
    assertThrowsExactly(
        LectureNotFoundException.class,
        () -> lectureService.updateLecture(WRONG_ID, updateRequestDto));
  }
}
