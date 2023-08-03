package com.maziarz.krystian.recruitment.lecture;

import com.maziarz.krystian.recruitment.lecture.dto.LectureRequestDto;
import com.maziarz.krystian.recruitment.lecture.dto.LectureResponseDto;
import java.util.List;

public interface LectureService {

  List<LectureResponseDto> getAllLectures();

  LectureResponseDto findById(Long id);

  void deleteById(Long id);

  LectureResponseDto addLecture(LectureRequestDto lectureRequestDto);

  LectureResponseDto updateLecture(Long id, LectureRequestDto lectureRequestDto);

  LectureEntity checkIfLectureExists(Long lectureId);
}
