package com.example.demo.Controller;

import com.example.demo.DTO.ResponseDTO;
import com.example.demo.DTO.SalaryDTO;
import com.example.demo.Exceptions.EmployeeNotFoundException;
import com.example.demo.Service.Salary.SalaryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salaries")
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @PostMapping("/{employeeId}")
    public ResponseEntity<?> createSalary(@Valid @RequestBody SalaryDTO salaryDTO, @PathVariable Long employeeId) throws EmployeeNotFoundException {
        SalaryDTO createdSalary = salaryService.createSalary(salaryDTO, employeeId);
        return ResponseEntity.ok(new ResponseDTO<SalaryDTO>(true, "Salary added successfully", createdSalary));
    }

    @GetMapping
    public ResponseEntity<?> getAllSalaries() {
        List<SalaryDTO> salaries = salaryService.getAllSalaries();
        return ResponseEntity.ok(new ResponseDTO<List<SalaryDTO>>(true, "Salaries fetched successfully", salaries));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSalaryById(@PathVariable Long id) {
        SalaryDTO salary = salaryService.getSalaryById(id);
        return ResponseEntity.ok(new ResponseDTO<SalaryDTO>(true, "Salary fetched successfully", salary));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSalary(@PathVariable Long id, @Valid @RequestBody SalaryDTO salaryDTO) {
        SalaryDTO updatedSalary = salaryService.updateSalary(id, salaryDTO);
        return ResponseEntity.ok(new ResponseDTO<SalaryDTO>(true, "Salary updated successfully", updatedSalary));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSalary(@PathVariable Long id) {
        salaryService.deleteSalary(id);
        return ResponseEntity.ok(new ResponseDTO<>(true, "Salary deleted successfully", null));
    }
}
