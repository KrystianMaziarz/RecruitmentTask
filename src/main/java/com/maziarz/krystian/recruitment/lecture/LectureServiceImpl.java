package com.maziarz.krystian.recruitment.lecture;

import com.maziarz.krystian.recruitment.lecture.dto.LectureRequestDto;
import com.maziarz.krystian.recruitment.lecture.dto.LectureResponseDto;
import com.maziarz.krystian.recruitment.lecture.exception.LectureNotFoundException;
import com.maziarz.krystian.recruitment.university.UniversityService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LectureServiceImpl implements LectureService {
  private final LectureRepository lectureRepository;
  private final UniversityService universityService;

  private final LectureMapper lectureMapper;

  @Override
  public List<LectureResponseDto> getAllLectures() {
    return lectureRepository.findAll().stream()
        .map(lectureMapper::mapLectureEntityToResponseDto)
        .toList();
  }

  @Override
  public LectureResponseDto findById(Long id) {

    LectureEntity lectureEntity =
        lectureRepository.findById(id).orElseThrow(() -> new LectureNotFoundException(id));

    return lectureMapper.mapLectureEntityToResponseDto(lectureEntity);
  }

  @Override
  public void deleteById(Long id) {
    lectureRepository.deleteById(id);
  }

  @Override
  @Transactional
  public LectureResponseDto addLecture(LectureRequestDto lectureRequestDto) {
    universityService.findById(lectureRequestDto.getUniversityId());
    LectureEntity lectureEntity = lectureMapper.mapLectureRequestDtoToEntity(lectureRequestDto);
    lectureEntity.addUniversity(lectureRequestDto.getUniversityId());
    lectureRepository.save(lectureEntity);
    return lectureMapper.mapLectureEntityToResponseDto(lectureEntity);
  }

  @Override
  @Transactional
  public LectureResponseDto updateLecture(Long id, LectureRequestDto lectureRequestDto) {

    LectureEntity lectureEntity =
        lectureRepository.findById(id).orElseThrow(() -> new LectureNotFoundException(id));
    lectureEntity.setName(lectureRequestDto.getName());
    lectureEntity.setLectureTime(lectureRequestDto.getLectureTime());
    lectureEntity.setDescription(lectureRequestDto.getDescription());
    return lectureMapper.mapLectureEntityToResponseDto(lectureEntity);
  }

  public LectureEntity checkIfLectureExists(Long lectureId) {

    return lectureRepository
        .findById(lectureId)
        .orElseThrow(() -> new LectureNotFoundException(lectureId));
  }
}
