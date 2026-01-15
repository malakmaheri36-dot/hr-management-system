package com.solutions.hrsystem.services;

import com.solutions.hrsystem.entities.Task;
import com.solutions.hrsystem.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public List<Task> getTasksByEmployee(Long employeeId) {
        return taskRepository.findByAssignedEmployee_EmployeeId(employeeId);
    }

    public List<Task> getTasksByProject(Long projectId) {
        return taskRepository.findByProject_Id(projectId);  // FIXED: Changed from findByProject_ProjectId
    }

    public List<Task> getTasksByStatus(String status) {
        return taskRepository.findByStatus(status);
    }

    public List<Task> getTasksByPriority(String priority) {
        return taskRepository.findByPriority(priority);
    }
}