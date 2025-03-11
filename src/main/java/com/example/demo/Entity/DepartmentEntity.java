

package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DepartmentEntity {

    @Id
    @Column(name = "departmentName")
    @NotBlank(message = "Name cannot be empty")
    private String departmentName;

    @PositiveOrZero(message = "Number of employees cannot be 0 or negative")
    @Column(name = "numberOfEmployees")
    private int numberOfEmployees;

    @OneToMany(mappedBy = "departmentEntity")
    @JsonIgnore
    private List<EmployeeEntity> employeeEntity = new ArrayList<>();
}
