package com.solutions.hrsystem.repositories;

import com.solutions.hrsystem.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByDepartment_DepartmentId(Long departmentId);
    List<Employee> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);
    Optional<Employee> findByUser_UserId(Long userId);

    @Query("SELECT e FROM Employee e WHERE e.position = ?1")
    List<Employee> findByPosition(String position);
}
