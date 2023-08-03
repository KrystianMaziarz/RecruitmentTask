package com.maziarz.krystian.recruitment.lecture.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LectureRequestDto {

  @NotBlank(message = "name is mandatory")
  private String name;

  @NotBlank(message = "description is mandatory")
  private String description;

  private LocalDateTime lectureTime;

  private Long universityId;
}
