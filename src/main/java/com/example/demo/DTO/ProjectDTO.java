package com.example.demo.DTO;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {

    @NotBlank(message = "Project name cannot be empty")
    private String projectName;

    @NotBlank(message = "Project description cannot be empty")
    private String description;

    @NotNull(message = "Project start date cannot be null")
    @PastOrPresent(message = "Project start date must be today or in the past")
    private LocalDate startDate;
}
