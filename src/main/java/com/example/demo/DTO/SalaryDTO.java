package com.example.demo.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryDTO {

    private Long id;

    @NotNull(message = "Basic salary cannot be null")
    @Min(value = 1, message = "Basicsalary should be more than 0")
    private double basicSalary;

    @NotNull(message = "HRA cannot be null")
    @Min(value = 1, message = "HRA should be more than 0")
    private double hra;

    @NotNull(message = "Bonus cannot be null")
    @Min(value = 1, message = "Bonus should be more then 0")
    private double bonus;
}
