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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
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
    StudentEntity studentEntity = checkIfStudentExists(id);

    return studentMapper.mapStudentEntityToResponseDto(studentEntity);
  }

  @Override
  public void deleteById(Long id) {

    studentRepository.deleteById(id);
  }

  @Override
  @Transactional
  public StudentResponseDto addStudent(StudentRequestDto studentRequestDto) {
    universityService.checkIfUniversityExists(studentRequestDto.getUniversityId());
    StudentEntity studentEntity = studentMapper.mapStudentRequestDtoToEntity(studentRequestDto);
    studentEntity.addUniversity(studentRequestDto.getUniversityId());
    studentRepository.save(studentEntity);
    return studentMapper.mapStudentEntityToResponseDto(studentEntity);
  }

  @Override
  @Transactional
  public StudentResponseDto updateStudent(Long id, StudentRequestDto studentRequestDto) {
    StudentEntity studentEntity = checkIfStudentExists(id);

    studentEntity.setFirstName(studentRequestDto.getFirstName());
    studentEntity.setLastName(studentRequestDto.getLastName());
    studentEntity.setGender(studentRequestDto.getGender());

    return studentMapper.mapStudentEntityToResponseDto(studentEntity);
  }

  @Override
  @Transactional
  public StudentAddLectureResponseDto addLectureToStudent(Long studentId, Long lectureId) {
    StudentEntity studentEntity = checkIfStudentExists(studentId);
    LectureEntity lectureEntity = lectureService.checkIfLectureExists(lectureId);
    studentEntity.addLecture(lectureEntity);
    return studentMapper.mapStudentEntityToStudentAddLectureResponseDto(
        studentEntity, lectureEntity.getName());
  }

  private StudentEntity checkIfStudentExists(Long studentId) {
    return studentRepository
        .findById(studentId)
        .orElseThrow(() -> new StudentNotFoundException(studentId));
  }
}
