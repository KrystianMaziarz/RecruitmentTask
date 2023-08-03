package com.maziarz.krystian.recruitment.university.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UniversityRequestDto {

  @NotBlank(message = "Name is mandatory")
  private String name;

  @NotBlank(message = "Address is mandatory")
  private String address;
}
