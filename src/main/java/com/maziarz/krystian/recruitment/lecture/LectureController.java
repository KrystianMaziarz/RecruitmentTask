package com.maziarz.krystian.recruitment.lecture;

import com.maziarz.krystian.recruitment.lecture.dto.LectureRequestDto;
import com.maziarz.krystian.recruitment.lecture.dto.LectureResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lectures")
@RequiredArgsConstructor
public class LectureController {

  private final LectureService lectureService;

  @GetMapping
  @Operation(description = "Get a list of all available lectures")
  public List<LectureResponseDto> getAllLectures() {

    return lectureService.getAllLectures();
  }

  @GetMapping("/{id}")
  @Operation(description = "Get single lecture by id")
  public LectureResponseDto getLecture(
      @PathVariable @Schema(description = "lecture id", example = "1") Long id) {

    return lectureService.findById(id);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(description = "Delete a lecture based on its id")
  public void deleteById(@PathVariable @Schema(description = "lecture id", example = "1") Long id) {

    lectureService.deleteById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(description = "Add a new lecture")
  public LectureResponseDto addLecture(
      @RequestBody @Valid @Schema(description = "Request body for adding a new lecture")
          LectureRequestDto lectureRequestDto) {

    return lectureService.addLecture(lectureRequestDto);
  }

  @PutMapping("/{id}")
  @Operation(description = "Update a lecture based on its id")
  public LectureResponseDto updateLecture(
      @PathVariable @Schema(description = "lecture id", example = "1") Long id,
      @RequestBody @Valid @Schema(description = "Request body for updating a lecture")
          LectureRequestDto lectureRequestDto) {

    return lectureService.updateLecture(id, lectureRequestDto);
  }
}
