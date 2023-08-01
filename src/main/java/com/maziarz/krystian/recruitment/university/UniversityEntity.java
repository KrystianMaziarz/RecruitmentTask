package com.maziarz.krystian.recruitment.university;

import com.maziarz.krystian.recruitment.lecture.LectureEntity;
import com.maziarz.krystian.recruitment.student.StudentEntity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "university")
public class UniversityEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String address;

  @OneToMany(mappedBy = "university", cascade = CascadeType.REMOVE)
  private List<StudentEntity> students = new ArrayList<>();

  @OneToMany(mappedBy = "university", cascade = CascadeType.REMOVE)
  private List<LectureEntity> lectures = new ArrayList<>();

  public UniversityEntity(Long id) {
    this.id = id;
  }

  public UniversityEntity(String name, String address) {
    this.name = name;
    this.address = address;
  }
}
