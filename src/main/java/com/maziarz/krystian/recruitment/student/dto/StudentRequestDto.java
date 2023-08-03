package com.maziarz.krystian.recruitment.student.dto;

import static com.maziarz.krystian.recruitment.commons.RecruitmentApp.REGEX_VALIDATION_FIRST_NAME;
import static com.maziarz.krystian.recruitment.commons.RecruitmentApp.REGEX_VALIDATION_LAST_NAME;

import com.maziarz.krystian.recruitment.student.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequestDto {

  @NotBlank(message = "First name is mandatory")
  @Pattern(
      regexp = REGEX_VALIDATION_FIRST_NAME,
      message = "Invalid First Name format, only Polish letters are valid for First Name.")
  private String firstName;

  @NotBlank(message = "Last name is mandatory")
  @Pattern(
      regexp = REGEX_VALIDATION_LAST_NAME,
      message = "Invalid Last Name format, only Polish letters are valid for Last Name.")
  private String lastName;

  private Gender gender;

  private Long universityId;
}
