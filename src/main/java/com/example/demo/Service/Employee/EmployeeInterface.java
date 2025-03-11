package com.example.demo.Service.Employee;

import com.example.demo.DTO.EmployeeDTO;
import com.example.demo.DTO.PageDTO;
import com.example.demo.Entity.EmployeeEntity;
import com.example.demo.Exceptions.EmployeeNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeInterface {
    List<EmployeeDTO> filterByDepartname(String decodedName);

    List<EmployeeDTO> filterBySlaryRange(int start, int end);

    List<EmployeeDTO> filterByQuery(String name);

    List<EmployeeDTO> findBySalaryGreaterThan(int amount);

    List<EmployeeDTO> findByProject(String name);

    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);

    Page<EmployeeEntity> getAllEmployees(PageDTO pageDTO);

    EmployeeDTO getEmployeeById(Long id);

    EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) throws EmployeeNotFoundException;

    void deleteEmployee(Long id) throws EmployeeNotFoundException;

    double getSalary(Long id);
}
