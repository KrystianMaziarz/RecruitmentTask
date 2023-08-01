package com.maziarz.krystian.recruitment.student;

import com.maziarz.krystian.recruitment.student.dto.StudentAddLectureResponseDto;
import com.maziarz.krystian.recruitment.student.dto.StudentRequestDto;
import com.maziarz.krystian.recruitment.student.dto.StudentResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {

  StudentResponseDto mapStudentEntityToResponseDto(StudentEntity entity);

  StudentEntity mapStudentRequestDtoToEntity(StudentRequestDto studentRequestDto);

  StudentAddLectureResponseDto mapStudentEntityToStudentAddLectureResponseDto(
      StudentEntity entity, String lectureName);
}
