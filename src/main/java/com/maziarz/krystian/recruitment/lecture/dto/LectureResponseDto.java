package com.maziarz.krystian.recruitment.lecture.dto;

import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LectureResponseDto {

  private Long id;

  private String name;
  private String description;

  private LocalDateTime lectureTime;
}
