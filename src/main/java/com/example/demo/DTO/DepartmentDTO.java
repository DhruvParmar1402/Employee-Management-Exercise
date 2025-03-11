package com.example.demo.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {

    @NotBlank(message = "Name cannot be empty")
    private String departmentName;

    @PositiveOrZero(message = "Number of employees cannot be 0 or negative")
    private int numberOfEmployees;
}
