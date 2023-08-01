package com.maziarz.krystian.recruitment.university;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface UniversityRepository extends JpaRepository<UniversityEntity, Long> {}
