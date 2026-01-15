package com.solutions.hrsystem.services;

import com.solutions.hrsystem.entities.Project;
import com.solutions.hrsystem.repositories.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    public Project saveProject(Project project) {
        if (project.getProgress() == null) {
            project.setProgress(0.0);
        }
        if (project.getStatus() == null) {
            project.setStatus("PLANNING");
        }
        return projectRepository.save(project);
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    public List<Project> getProjectsByDepartment(Long departmentId) {
        return projectRepository.findByDepartment_DepartmentId(departmentId);
    }

    public List<Project> getProjectsByManager(Long managerId) {
        return projectRepository.findByManager_EmployeeId(managerId);
    }

    public List<Project> getProjectsByStatus(String status) {
        return projectRepository.findByStatus(status);
    }

    public Project updateProjectProgress(Long id, Double progress) {
        Optional<Project> projectOpt = projectRepository.findById(id);
        if (projectOpt.isPresent()) {
            Project project = projectOpt.get();
            project.setProgress(Math.min(100.0, Math.max(0.0, progress)));

            if (progress >= 100.0) {
                project.setStatus("COMPLETED");
            } else if (progress > 0.0 && "PLANNING".equals(project.getStatus())) {
                project.setStatus("IN_PROGRESS");
            }
            return projectRepository.save(project);
        }
        throw new RuntimeException("Project not found with id: " + id);
    }
}
