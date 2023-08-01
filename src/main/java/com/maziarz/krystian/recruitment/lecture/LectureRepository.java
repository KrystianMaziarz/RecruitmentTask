package com.maziarz.krystian.recruitment.lecture;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface LectureRepository extends JpaRepository<LectureEntity, Long> {}
