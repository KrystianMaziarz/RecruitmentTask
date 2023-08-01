package com.maziarz.krystian.recruitment.university;

import com.maziarz.krystian.recruitment.university.dto.UniversityRequestDto;
import com.maziarz.krystian.recruitment.university.dto.UniversityResponseDto;
import java.util.List;

public interface UniversityService {

  List<UniversityResponseDto> getAllUniversities();

  UniversityResponseDto findById(Long id);

  void deleteById(Long id);

  UniversityResponseDto addUniversity(UniversityRequestDto universityRequestDto);

  UniversityResponseDto updateUniversity(Long id, UniversityRequestDto universityRequestDto);

  void checkIfUniversityExists(Long id);
}
