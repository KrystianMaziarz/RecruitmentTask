package com.maziarz.krystian.recruitment.student;

import com.maziarz.krystian.recruitment.student.dto.StudentAddLectureResponseDto;
import com.maziarz.krystian.recruitment.student.dto.StudentRequestDto;
import com.maziarz.krystian.recruitment.student.dto.StudentResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

  private final StudentService studentService;

  @GetMapping
  @Operation(description = "Get a list of all available students")
  public List<StudentResponseDto> getAllStudents() {

    return studentService.getAllStudents();
  }

  @GetMapping("/{id}")
  @Operation(description = "Get single student based on its id")
  public StudentResponseDto getStudentById(@PathVariable Long id) {

    return studentService.findById(id);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(description = "Delete a student based on its id")
  public void deleteById(@PathVariable @Schema(description = "student id", example = "1") Long id) {

    studentService.deleteById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(description = "Add a new student")
  public StudentResponseDto addStudent(
      @RequestBody @Valid @Schema(description = "Request body for adding a new student")
          StudentRequestDto studentRequestDto) {

    return studentService.addStudent(studentRequestDto);
  }

  @PutMapping("/{id}")
  public StudentResponseDto updateStudent(
      @PathVariable @Schema(description = "student id", example = "1") Long id,
      @RequestBody @Valid @Schema(description = "Request body for updating a student")
          StudentRequestDto studentRequestDto) {

    return studentService.updateStudent(id, studentRequestDto);
  }

  @PutMapping("/addLecture/{studentId}")
  @Operation(description = "Add a lecture to student")
  public StudentAddLectureResponseDto addLectureForStudent(
      @PathVariable @Schema(description = "student id", example = "44") Long studentId,
      @RequestParam @Schema(description = "lecture id", example = "15") Long lectureId) {

    return studentService.addLectureToStudent(studentId, lectureId);
  }
}
