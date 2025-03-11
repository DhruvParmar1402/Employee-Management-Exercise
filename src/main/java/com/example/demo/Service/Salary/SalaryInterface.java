package com.example.demo.Service.Salary;

import com.example.demo.DTO.SalaryDTO;
import com.example.demo.Entity.SalaryEntity;
import com.example.demo.Exceptions.EmployeeNotFoundException;

import java.util.List;

public interface SalaryInterface {
    SalaryDTO createSalary(SalaryDTO salaryDTO, Long employeeId) throws EmployeeNotFoundException;

    List<SalaryDTO> getAllSalaries();

    SalaryDTO getSalaryById(Long id);

    SalaryDTO updateSalary(Long id, SalaryDTO salaryDTO);

    void deleteSalary(Long id);
}
