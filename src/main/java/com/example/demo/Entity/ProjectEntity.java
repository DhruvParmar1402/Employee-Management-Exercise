package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProjectEntity {

    @Id
    @NotBlank(message = "Project name cannot be empty")
    private String projectName;

    @NotBlank(message = "Project description cannot be empty")
    private String description;

    @NotNull(message = "Project start date cannot be null")
    @PastOrPresent(message = "Project start date must be today or in the future")
    private LocalDate startDate;

    @ManyToMany(mappedBy = "projectsEntity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<EmployeeEntity> employeeEntity = new ArrayList<>();
}
