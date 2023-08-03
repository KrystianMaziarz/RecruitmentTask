package com.maziarz.krystian.recruitment.lecture;

import com.maziarz.krystian.recruitment.lecture.dto.LectureRequestDto;
import com.maziarz.krystian.recruitment.lecture.dto.LectureResponseDto;
import com.maziarz.krystian.recruitment.lecture.exception.LectureNotFoundException;
import com.maziarz.krystian.recruitment.university.UniversityService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
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
    log.info("Lecture deleted. id: {}", id);
  }

  @Override
  @Transactional
  public LectureResponseDto addLecture(LectureRequestDto lectureRequestDto) {
    universityService.findById(lectureRequestDto.getUniversityId());
    LectureEntity lectureEntity = lectureMapper.mapLectureRequestDtoToEntity(lectureRequestDto);
    lectureEntity.addUniversity(lectureRequestDto.getUniversityId());
    LectureEntity savedEntity = lectureRepository.save(lectureEntity);
    log.info("Lecture created. id: {}", savedEntity.getId());
    return lectureMapper.mapLectureEntityToResponseDto(savedEntity);
  }

  @Override
  @Transactional
  public LectureResponseDto updateLecture(Long id, LectureRequestDto lectureRequestDto) {

    LectureEntity lectureEntity =
        lectureRepository.findById(id).orElseThrow(() -> new LectureNotFoundException(id));
    lectureEntity.setName(lectureRequestDto.getName());
    lectureEntity.setLectureTime(lectureRequestDto.getLectureTime());
    lectureEntity.setDescription(lectureRequestDto.getDescription());
    log.info("Lecture updated. id: {}", lectureEntity.getId());
    return lectureMapper.mapLectureEntityToResponseDto(lectureEntity);
  }

  public LectureEntity checkIfLectureExists(Long lectureId) {

    return lectureRepository
        .findById(lectureId)
        .orElseThrow(() -> new LectureNotFoundException(lectureId));
  }
}
