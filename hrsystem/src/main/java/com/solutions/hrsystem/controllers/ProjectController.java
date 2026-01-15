package com.solutions.hrsystem.controllers;

import com.solutions.hrsystem.entities.Project;
import com.solutions.hrsystem.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Project createProject(@RequestBody Project project) {
        return projectService.saveProject(project);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project projectDetails) {
        return projectService.getProjectById(id)
                .map(project -> {
                    project.setProjectName(projectDetails.getProjectName());
                    project.setDescription(projectDetails.getDescription());
                    project.setStartDate(projectDetails.getStartDate());
                    project.setEndDate(projectDetails.getEndDate());
                    project.setStatus(projectDetails.getStatus());
                    project.setBudget(projectDetails.getBudget());
                    project.setProgress(projectDetails.getProgress());
                    return ResponseEntity.ok(projectService.saveProject(project));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/department/{departmentId}")
    public List<Project> getProjectsByDepartment(@PathVariable Long departmentId) {
        return projectService.getProjectsByDepartment(departmentId);
    }

    @GetMapping("/manager/{managerId}")
    public List<Project> getProjectsByManager(@PathVariable Long managerId) {
        return projectService.getProjectsByManager(managerId);
    }

    @GetMapping("/status/{status}")
    public List<Project> getProjectsByStatus(@PathVariable String status) {
        return projectService.getProjectsByStatus(status);
    }

    @PutMapping("/{id}/progress")
    public ResponseEntity<Project> updateProgress(@PathVariable Long id, @RequestParam Double progress) {
        return ResponseEntity.ok(projectService.updateProjectProgress(id, progress));
    }
}
