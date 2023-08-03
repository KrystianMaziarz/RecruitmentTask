package com.maziarz.krystian.recruitment.student;

import com.maziarz.krystian.recruitment.lecture.LectureEntity;
import com.maziarz.krystian.recruitment.university.UniversityEntity;
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
@Table(name = "student")
public class StudentEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String firstName;
  private String lastName;

  @Enumerated(EnumType.STRING)
  private Gender gender;

  @ManyToOne
  @JoinColumn(name = "university_id")
  private UniversityEntity university;

  @ManyToMany
  @JoinTable(
      name = "student_lecture",
      joinColumns = @JoinColumn(name = "student_id"),
      inverseJoinColumns = @JoinColumn(name = "lecture_id"))
  private List<LectureEntity> lectures = new ArrayList<>();

  void addUniversity(Long universityId) {
    this.university = new UniversityEntity(universityId);
  }

  void addLecture(LectureEntity lectureEntity) {
    lectures.add(lectureEntity);
    lectureEntity.getStudents().add(this);
  }

  public StudentEntity(
      String firstName,
      String lastName,
      Gender gender,
      UniversityEntity university,
      List<LectureEntity> lectures) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    this.university = university;
    this.lectures = lectures;
  }
}
