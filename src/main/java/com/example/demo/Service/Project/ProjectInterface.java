package com.example.demo.Service.Project;

import com.example.demo.DTO.ProjectDTO;

import java.util.List;

public interface ProjectInterface {
    ProjectDTO createProject(ProjectDTO projectDTO);

    List<ProjectDTO> getAllProjects();

    ProjectDTO getProjectByName(String projectName);

    ProjectDTO updateProject(String projectName, ProjectDTO updatedProjectDTO);

    void deleteProject(String projectName);
}
