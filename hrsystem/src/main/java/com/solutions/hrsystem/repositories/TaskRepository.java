package com.solutions.hrsystem.repositories;

import com.solutions.hrsystem.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Use the actual ID field name from Employee entity
    List<Task> findByAssignedEmployee_EmployeeId(Long employeeId);

    // Project entity uses 'id' as the ID field (not projectId)
    List<Task> findByProject_Id(Long projectId);

    List<Task> findByStatus(String status);

    List<Task> findByPriority(String priority);
}