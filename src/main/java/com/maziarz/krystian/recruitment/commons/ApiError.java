package com.maziarz.krystian.recruitment.commons;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Value;

@Value
public class ApiError {

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  LocalDateTime localDateTime;

  String message;
}
