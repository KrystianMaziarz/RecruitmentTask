package com.maziarz.krystian.recruitment.lecture;

import com.maziarz.krystian.recruitment.student.StudentEntity;
import com.maziarz.krystian.recruitment.university.UniversityEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
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
@Table(name = "lecture")
public class LectureEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String description;

  private LocalDateTime lectureTime;

  @ManyToOne
  @JoinColumn(name = "university_id")
  private UniversityEntity university;

  @ManyToMany(mappedBy = "lectures")
  private List<StudentEntity> students = new ArrayList<>();

  void addUniversity(Long universityId) {
    this.university = new UniversityEntity(universityId);
  }
}
