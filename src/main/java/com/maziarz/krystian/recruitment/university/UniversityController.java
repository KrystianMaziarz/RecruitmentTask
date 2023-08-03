package com.maziarz.krystian.recruitment.university;

import com.maziarz.krystian.recruitment.university.dto.UniversityRequestDto;
import com.maziarz.krystian.recruitment.university.dto.UniversityResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/universities")
@RequiredArgsConstructor
public class UniversityController {

  private final UniversityService universityService;

  @GetMapping
  @Operation(description = "Get a list of all available universities")
  public List<UniversityResponseDto> getAll() {

    return universityService.getAllUniversities();
  }

  @GetMapping("/{id}")
  @Operation(description = "Get single university by id")
  public UniversityResponseDto getById(
      @PathVariable @Schema(description = "university id", example = "15") Long id) {

    return universityService.findById(id);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(description = "Delete a university based on its id")
  public void deleteById(
      @PathVariable @Schema(description = "university id", example = "15") Long id) {

    universityService.deleteById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(description = "Add a new university")
  public UniversityResponseDto addUniversity(
      @RequestBody @Valid @Schema(description = "Request body for adding a new university")
          UniversityRequestDto universityRequestDto) {

    return universityService.addUniversity(universityRequestDto);
  }

  @PutMapping("/{id}")
  @Operation(description = "Update a university based on its id")
  public UniversityResponseDto updateUniversity(
      @PathVariable @Schema(description = "university id", example = "13") Long id,
      @RequestBody @Valid @Schema(description = "Request body for updating a university")
          UniversityRequestDto universityRequestDto) {

    return universityService.updateUniversity(id, universityRequestDto);
  }
}
