package com.example.demo.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    private Long id;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Address cannot be empty")
    private String address;

    @NotBlank(message = "Gender cannot be null")
    private String gender;

    @NotBlank(message = "Occupation cannot be empty")
    private String occupation;

    @NotNull(message = "Department ID is required")
    private String departmentId;

    @Valid
    @NotNull(message = "Salary ID is required")
    private SalaryDTO salaryId;

    @NotEmpty(message = "At least one project ID is required")
    private List<String> projectIds;
}
