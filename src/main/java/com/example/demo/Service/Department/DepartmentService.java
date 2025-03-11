package com.example.demo.Service.Department;

import com.example.demo.DTO.DepartmentDTO;
import com.example.demo.Entity.DepartmentEntity;
import com.example.demo.Repository.DepartmentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService implements DepartmentInterface {

    private final DepartmentRepository departmentRepository;

    @Transactional
    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        DepartmentEntity department = new DepartmentEntity();
        department.setDepartmentName(departmentDTO.getDepartmentName());
        department.setNumberOfEmployees(departmentDTO.getNumberOfEmployees());
        departmentRepository.save(department);
        return departmentDTO;
    }

    public List<DepartmentDTO> getAllDepartments() {
        List<DepartmentEntity> departments = departmentRepository.findAll();
        return departments.stream()
                .map(dept -> new DepartmentDTO(dept.getDepartmentName(), dept.getNumberOfEmployees()))
                .collect(Collectors.toList());
    }

    public DepartmentDTO getDepartmentByName(String departmentName) {
        DepartmentEntity department = departmentRepository.findById(departmentName)
                .orElseThrow(() -> new EntityNotFoundException("Department not found: " + departmentName));

        return new DepartmentDTO(department.getDepartmentName(), department.getNumberOfEmployees());
    }

    @Transactional
    public DepartmentDTO updateDepartment(String departmentName, DepartmentDTO departmentDTO) {
        System.out.println(departmentName);
        System.out.println(departmentDTO.getDepartmentName());
        if (!departmentName.equals(departmentDTO.getDepartmentName())) {
            throw new EntityNotFoundException("Department mismatch.");
        }
        DepartmentEntity department = departmentRepository.findById(departmentName)
                .orElseThrow(() -> new EntityNotFoundException("Department not found: " + departmentName));

        department.setNumberOfEmployees(departmentDTO.getNumberOfEmployees());
        departmentRepository.save(department);
        return departmentDTO;
    }

    public void deleteDepartment(String departmentName) {
        if (!departmentRepository.existsById(departmentName)) {
            throw new EntityNotFoundException("Department not found: " + departmentName);
        }
        departmentRepository.deleteById(departmentName);
    }
}
