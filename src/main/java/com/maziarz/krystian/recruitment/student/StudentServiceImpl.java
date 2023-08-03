package com.maziarz.krystian.recruitment.student;

import com.maziarz.krystian.recruitment.lecture.LectureEntity;
import com.maziarz.krystian.recruitment.lecture.LectureService;
import com.maziarz.krystian.recruitment.student.dto.StudentAddLectureResponseDto;
import com.maziarz.krystian.recruitment.student.dto.StudentRequestDto;
import com.maziarz.krystian.recruitment.student.dto.StudentResponseDto;
import com.maziarz.krystian.recruitment.student.exception.StudentNotFoundException;
import com.maziarz.krystian.recruitment.university.UniversityService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {

  private final StudentRepository studentRepository;
  private final StudentMapper studentMapper;
  private final UniversityService universityService;
  private final LectureService lectureService;

  @Override
  public List<StudentResponseDto> getAllStudents() {
    List<StudentEntity> listOfStudents = studentRepository.findAll();
    return listOfStudents.stream().map(studentMapper::mapStudentEntityToResponseDto).toList();
  }

  @Override
  public StudentResponseDto findById(Long id) {
    StudentEntity studentEntity = getStudentOrThrowStudentNotFoundException(id);

    return studentMapper.mapStudentEntityToResponseDto(studentEntity);
  }

  @Override
  public void deleteById(Long id) {

    studentRepository.deleteById(id);
    log.info("Student deleted. id: {}", id);
  }

  @Override
  @Transactional
  public StudentResponseDto addStudent(StudentRequestDto studentRequestDto) {
    universityService.checkIfUniversityExists(studentRequestDto.getUniversityId());
    StudentEntity studentEntity = studentMapper.mapStudentRequestDtoToEntity(studentRequestDto);
    studentEntity.addUniversity(studentRequestDto.getUniversityId());
    StudentEntity savedEntity = studentRepository.save(studentEntity);
    log.info("Student created. id: {}", savedEntity.getId());
    return studentMapper.mapStudentEntityToResponseDto(studentEntity);
  }

  @Override
  @Transactional
  public StudentResponseDto updateStudent(Long id, StudentRequestDto studentRequestDto) {
    StudentEntity studentEntity = getStudentOrThrowStudentNotFoundException(id);

    studentEntity.setFirstName(studentRequestDto.getFirstName());
    studentEntity.setLastName(studentRequestDto.getLastName());
    studentEntity.setGender(studentRequestDto.getGender());
    log.info("Student updated. id: {}", studentEntity.getId());
    return studentMapper.mapStudentEntityToResponseDto(studentEntity);
  }

  @Override
  @Transactional
  public StudentAddLectureResponseDto addLectureToStudent(Long studentId, Long lectureId) {
    StudentEntity studentEntity = getStudentOrThrowStudentNotFoundException(studentId);
    LectureEntity lectureEntity = lectureService.checkIfLectureExists(lectureId);
    studentEntity.addLecture(lectureEntity);
    log.info(
        "Lecture with id: {} added to student with id: {}",
        lectureEntity.getId(),
        studentEntity.getId());
    return studentMapper.mapStudentEntityToStudentAddLectureResponseDto(
        studentEntity, lectureEntity.getName());
  }

  private StudentEntity getStudentOrThrowStudentNotFoundException(Long studentId) {
    return studentRepository
        .findById(studentId)
        .orElseThrow(() -> new StudentNotFoundException(studentId));
  }
}
