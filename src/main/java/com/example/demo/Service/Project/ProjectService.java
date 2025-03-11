package com.example.demo.Service.Project;

import com.example.demo.DTO.ProjectDTO;
import com.example.demo.Entity.ProjectEntity;
import com.example.demo.Repository.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService implements ProjectInterface {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Transactional
    public ProjectDTO createProject(ProjectDTO projectDTO) {
        ProjectEntity project = new ProjectEntity(
                projectDTO.getProjectName(),
                projectDTO.getDescription(),
                projectDTO.getStartDate(),
                null
        );
        projectRepository.save(project);
        return projectDTO;
    }

    public List<ProjectDTO> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(p -> new ProjectDTO(
                        p.getProjectName(),
                        p.getDescription(),
                        p.getStartDate()
                ))
                .collect(Collectors.toList());
    }

    public ProjectDTO getProjectByName(String projectName) {
        ProjectEntity project = projectRepository.findById(projectName)
                .orElseThrow(() -> new EntityNotFoundException("Project not found: " + projectName));

        return new ProjectDTO(
                project.getProjectName(),
                project.getDescription(),
                project.getStartDate()
        );
    }

    @Transactional
    public ProjectDTO updateProject(String projectName, ProjectDTO updatedProjectDTO) {
        if (!projectName.equals(updatedProjectDTO.getProjectName())) {
            throw new EntityNotFoundException("Project name mismatch.");
        }

        ProjectEntity project = projectRepository.findById(projectName)
                .orElseThrow(() -> new EntityNotFoundException("Project not found: " + projectName));

        project.setDescription(updatedProjectDTO.getDescription());
        project.setStartDate(updatedProjectDTO.getStartDate());

        projectRepository.save(project);
        return updatedProjectDTO;
    }

    public void deleteProject(String projectName) {
        ProjectEntity project = projectRepository.findById(projectName)
                .orElseThrow(() -> new EntityNotFoundException("Project not found: " + projectName));

        projectRepository.delete(project);
    }
}
