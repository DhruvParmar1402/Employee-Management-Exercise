package com.example.demo.Service.Department;

import com.example.demo.DTO.DepartmentDTO;

import java.util.List;

public interface DepartmentInterface {
    DepartmentDTO createDepartment(DepartmentDTO departmentDTO);

    List<DepartmentDTO> getAllDepartments();

    DepartmentDTO getDepartmentByName(String departmentName);

    DepartmentDTO updateDepartment(String departmentName, DepartmentDTO departmentDTO);

    void deleteDepartment(String departmentName);
}
