package com.maziarz.krystian.recruitment.student;

import org.springframework.data.jpa.repository.JpaRepository;

interface StudentRepository extends JpaRepository<StudentEntity, Long> {}
