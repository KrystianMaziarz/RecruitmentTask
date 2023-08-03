package com.maziarz.krystian.recruitment.university;

import static com.maziarz.krystian.recruitment.student.StudentDataFactory.WRONG_UNIVERSITY_ID;
import static com.maziarz.krystian.recruitment.university.UniversityDataFactory.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.maziarz.krystian.recruitment.university.exception.UniversityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;

class UniversityServiceImplTest {
  private final UniversityRepository universityRepository = mock(UniversityRepository.class);
  private final UniversityMapper universityMapper = Mappers.getMapper(UniversityMapper.class);

  private final UniversityService universityService =
      new UniversityServiceImpl(universityRepository, universityMapper);

  @Test
  void shouldReturnListOfAllUniversities() {
    // given
    var universitiesList = List.of(getUniversityEntity());
    var expected = getUniversityResponseDto();
    when(universityRepository.findAll()).thenReturn(universitiesList);
    // when
    var result = universityService.getAllUniversities();
    // then
    assertThat(result.size()).isOne();
    assertThat(result.get(0)).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void shouldFindUniversityById() {
    // given
    var expected = getUniversityResponseDto();
    var optionalUniversity = Optional.of(getUniversityEntity());
    when(universityRepository.findById(UNIVERSITY_ID)).thenReturn(optionalUniversity);
    // when
    var result = universityService.findById(UNIVERSITY_ID);
    // then
    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void shouldDeleteUniversityFromRepository() {
    // given//when
    universityService.deleteById(UNIVERSITY_ID);
    // then
    verify(universityRepository).deleteById(UNIVERSITY_ID);
  }

  @Test
  void shouldAddNewUniversity() {
    // given
    var argumentCaptor = ArgumentCaptor.forClass(UniversityEntity.class);
    var requestDtoForAddUniversity = getUniversityRequestDtoForAddUniversity();
    var expected = getUniversityEntityWithoutId();
    when(universityRepository.save(any(UniversityEntity.class))).thenReturn(expected);
    // when
    universityService.addUniversity(requestDtoForAddUniversity);
    // then
    verify(universityRepository).save(argumentCaptor.capture());
    UniversityEntity result = argumentCaptor.getValue();

    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void shouldUpdateUniversity() {
    // given
    var optionalUniversityEntity = Optional.of(getUniversityEntity());
    var requestUniversityForUpdate = getUniversityRequestDtoForUpdateUniversity();
    var expected = getUniversityResponseAfterUpdate();
    when(universityRepository.findById(UNIVERSITY_ID)).thenReturn(optionalUniversityEntity);
    // when
    var result = universityService.updateUniversity(UNIVERSITY_ID, requestUniversityForUpdate);
    // then
    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void shouldThrowUniversityNotFoundExceptionWhenUniversityNotExists() {
    // given
    when(universityRepository.findById(WRONG_UNIVERSITY_ID)).thenReturn(Optional.empty());
    // when //then
    assertThrowsExactly(
        UniversityNotFoundException.class, () -> universityService.findById(WRONG_UNIVERSITY_ID));
  }

  @Test
  void shouldThrowUniversityNotFoundExceptionWhenTryingToUpdateNotExistingUniversity() {
    // given
    var updateRequestDto = getUniversityRequestDtoForUpdateUniversity();
    when(universityRepository.findById(WRONG_UNIVERSITY_ID)).thenReturn(Optional.empty());
    // when //then
    assertThrowsExactly(
        UniversityNotFoundException.class,
        () -> universityService.updateUniversity(WRONG_UNIVERSITY_ID, updateRequestDto));
  }

  @Test
  void shouldThrowExceptionWhenExistingByReturnFalse() {
    // given
    when(universityRepository.existsById(WRONG_UNIVERSITY_ID)).thenReturn(false);
    // when //then
    assertThrowsExactly(
        UniversityNotFoundException.class,
        () -> universityService.checkIfUniversityExists(WRONG_UNIVERSITY_ID));
  }

  @Test
  void shouldThrowExceptionWhenExistingByReturnTrue() {
    // given
    when(universityRepository.existsById(UNIVERSITY_ID)).thenReturn(true);
    // when //then
    assertDoesNotThrow(() -> universityService.checkIfUniversityExists(UNIVERSITY_ID));
  }
}
