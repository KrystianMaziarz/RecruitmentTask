package com.maziarz.krystian.recruitment.student;

import com.maziarz.krystian.recruitment.student.dto.StudentAddLectureResponseDto;
import com.maziarz.krystian.recruitment.student.dto.StudentRequestDto;
import com.maziarz.krystian.recruitment.student.dto.StudentResponseDto;
import java.util.List;

public interface StudentService {

  List<StudentResponseDto> getAllStudents();

  StudentResponseDto findById(Long id);

  void deleteById(Long id);

  StudentResponseDto addStudent(StudentRequestDto studentRequestDto);

  StudentResponseDto updateStudent(Long id, StudentRequestDto studentRequestDto);

  StudentAddLectureResponseDto addLectureToStudent(Long studentId, Long lectureId);
}
