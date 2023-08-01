package com.maziarz.krystian.recruitment.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface StudentRepository extends JpaRepository<StudentEntity, Long> {}
