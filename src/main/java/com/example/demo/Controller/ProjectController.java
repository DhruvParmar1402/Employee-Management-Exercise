package com.example.demo.Controller;

import com.example.demo.DTO.ProjectDTO;
import com.example.demo.DTO.ResponseDTO;
import com.example.demo.Service.Project.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<?> createProject(@Valid @RequestBody ProjectDTO projectDTO) {
        return ResponseEntity.ok(new ResponseDTO<ProjectDTO>(true, "Project created successfully", projectService.createProject(projectDTO)));
    }

    @GetMapping
    public ResponseEntity<?> getAllProjects() {
        return ResponseEntity.ok(new ResponseDTO<List<ProjectDTO>>(true, "Project fetched successfully", projectService.getAllProjects()));
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getProjectByName(@PathVariable String name) {
        String decodedName = URLDecoder.decode(name, StandardCharsets.UTF_8);
        return ResponseEntity.ok(new ResponseDTO<ProjectDTO>(true, "Project fetched successfully", projectService.getProjectByName(name)));
    }

    @PutMapping("/{name}")
    public ResponseEntity<?> updateProject(@PathVariable String name, @Valid @RequestBody ProjectDTO projectDTO) {
        String decodedName = URLDecoder.decode(name, StandardCharsets.UTF_8);
        return ResponseEntity.ok(new ResponseDTO<ProjectDTO>(true, "Project updated successfully", projectService.updateProject(decodedName, projectDTO)));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> deleteProject(@PathVariable String name) {
        String decodedName = URLDecoder.decode(name, StandardCharsets.UTF_8);
        projectService.deleteProject(decodedName);
        return ResponseEntity.ok(new ResponseDTO<>(true, "Project deleted successfully", null));
    }
}
