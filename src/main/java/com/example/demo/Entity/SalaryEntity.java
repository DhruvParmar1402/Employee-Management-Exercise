package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SalaryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "basicSalary")
    @NotNull(message = "Basic salary cannot be null")
    private double basicSalary;


    @Column(name = "hra")
    @NotNull(message = "HRA cannot be null")
    private double hra;

    @Column(name = "bonus")
    @NotNull(message = "Bonus cannot be null")
    private double bonus;

    @OneToOne
    @JoinColumn(name = "employee_id")
    @JsonIgnore
    private EmployeeEntity employeeEntity;

    public SalaryEntity(@NotNull(message = "Basic salary cannot be null") double basicSalary, @NotNull(message = "HRA cannot be null") double hra, @NotNull(message = "Bonus cannot be null") double bonus, EmployeeEntity employee) {
        this.basicSalary = basicSalary;
        this.hra = hra;
        this.bonus = bonus;
        this.employeeEntity = employee;
    }
}
