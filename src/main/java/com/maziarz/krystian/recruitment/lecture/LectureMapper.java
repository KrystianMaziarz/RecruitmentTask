package com.maziarz.krystian.recruitment.lecture;

import com.maziarz.krystian.recruitment.lecture.dto.LectureRequestDto;
import com.maziarz.krystian.recruitment.lecture.dto.LectureResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LectureMapper {

  LectureResponseDto mapLectureEntityToResponseDto(LectureEntity entity);

  LectureEntity mapLectureRequestDtoToEntity(LectureRequestDto lectureRequestDto);
}
