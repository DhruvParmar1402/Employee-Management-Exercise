package com.example.demo.Entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

@NamedQuery(
        name = "EmployeeEntity.findByDepartment",
        query = "SELECT e FROM EmployeeEntity e JOIN e.departmentEntity d WHERE d.departmentName = :deptName"
)
@NamedQuery(
        name = "EmployeeEntity.findBySalaryGreaterThan",
        query = "SELECT e FROM EmployeeEntity e JOIN e.salaryEntity s WHERE s.basicSalary > :salary"
)
@NamedQuery(
        name = "EmployeeEntity.findByProject",
        query = "SELECT e FROM EmployeeEntity e JOIN e.projectsEntity p WHERE p.projectName = :projectName"
)

public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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

    @ManyToOne
    @JoinColumn(name = "department_id")
    @Valid
    private DepartmentEntity departmentEntity;

    @OneToOne(mappedBy = "employeeEntity", cascade = CascadeType.ALL)
    @Valid
    private SalaryEntity salaryEntity;

    @ManyToMany
    @Valid
    @JoinTable(
            name = "employee_project",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private List<ProjectEntity> projectsEntity = new ArrayList<>();
}
