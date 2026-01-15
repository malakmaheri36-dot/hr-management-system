package com.solutions.hrsystem.repositories;

import com.solutions.hrsystem.entities.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {
    Optional<Resume> findByEmployee_EmployeeId(Long employeeId);
}
