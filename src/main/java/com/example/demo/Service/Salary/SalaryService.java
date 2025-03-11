package com.example.demo.Service.Salary;

import com.example.demo.DTO.SalaryDTO;
import com.example.demo.Entity.EmployeeEntity;
import com.example.demo.Entity.SalaryEntity;
import com.example.demo.Exceptions.EmployeeNotFoundException;
import com.example.demo.Repository.EmployeeRepository;
import com.example.demo.Repository.SalaryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SalaryService implements SalaryInterface {

    @Autowired
    private SalaryRepository salaryRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    public SalaryDTO createSalary(SalaryDTO salaryDTO, Long employeeId) throws EmployeeNotFoundException {
        SalaryEntity salary = new SalaryEntity();
        salary.setBasicSalary(salaryDTO.getBasicSalary());
        salary.setHra(salaryDTO.getHra());
        salary.setBonus(salaryDTO.getBonus());
        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
        salary.setEmployeeEntity(employee);
        salaryRepository.save(salary);
        return fromEntity(salary);
    }

    public List<SalaryDTO> getAllSalaries() {
        List<SalaryEntity> salaryEntities = salaryRepository.findAll();
        List<SalaryDTO> salaryDTOList = new ArrayList<>();
        for (SalaryEntity salary : salaryEntities) {
            salaryDTOList.add(fromEntity(salary));
        }
        return salaryDTOList;
    }

    public SalaryDTO getSalaryById(Long id) {
        SalaryEntity salary = salaryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Salary not found"));
        return fromEntity(salary);
    }

    @Transactional
    public SalaryDTO updateSalary(Long id, SalaryDTO salaryDTO) {
        SalaryEntity salary = salaryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Salary not found"));

        salary.setBasicSalary(salaryDTO.getBasicSalary());
        salary.setHra(salaryDTO.getHra());
        salary.setBonus(salaryDTO.getBonus());
        salaryRepository.save(salary);
        return fromEntity(salary);
    }

    public void deleteSalary(Long id) {
        SalaryEntity salary = salaryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Salary not found"));
        salaryRepository.delete(salary);
    }

    private SalaryDTO fromEntity(SalaryEntity salary) {
        SalaryDTO salaryDTO = new SalaryDTO();
        salaryDTO.setId((long) salary.getId());
        salaryDTO.setBasicSalary(salary.getBasicSalary());
        salaryDTO.setHra(salary.getHra());
        salaryDTO.setBonus(salary.getBonus());
        return salaryDTO;
    }
}
