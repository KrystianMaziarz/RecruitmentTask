package com.maziarz.krystian.recruitment.student.dto;

import com.maziarz.krystian.recruitment.student.Gender;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponseDto {
  private Long id;
  private String firstName;
  private String lastName;
  private Gender gender;
}
