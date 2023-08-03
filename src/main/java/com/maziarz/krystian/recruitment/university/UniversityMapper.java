package com.maziarz.krystian.recruitment.university;

import com.maziarz.krystian.recruitment.university.dto.UniversityRequestDto;
import com.maziarz.krystian.recruitment.university.dto.UniversityResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UniversityMapper {
  UniversityResponseDto mapUniversityEntityToResponseDto(UniversityEntity entity);

  UniversityEntity mapUniversityRequestDtoToEntity(UniversityRequestDto universityRequestDto);
}
