package com.example.demo.Controller;

import com.example.demo.DTO.EmployeeDTO;
import com.example.demo.DTO.PageDTO;
import com.example.demo.DTO.ResponseDTO;
import com.example.demo.Entity.EmployeeEntity;
import com.example.demo.Exceptions.EmployeeNotFoundException;
import com.example.demo.Service.Employee.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;


@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/filterbyquery/{name}")
    public ResponseEntity<?> filterByNamedQuery(@PathVariable String name) {
        String decodedName = URLDecoder.decode(name, StandardCharsets.UTF_8);
        return ResponseEntity.ok(new ResponseDTO<List<EmployeeDTO>>(true, "Filterd with the help of the named query", employeeService.filterByQuery(decodedName)));
    }

    @GetMapping("/greaterThan/{amount}")
    public ResponseEntity<?> filterBySalary(@PathVariable int amount) {
        return ResponseEntity.ok(new ResponseDTO<List<EmployeeDTO>>(true, "Filterd with the help of the named query", employeeService.findBySalaryGreaterThan(amount)));
    }

    @GetMapping("/filterbyproject/{name}")
    public ResponseEntity<?> filterByProject(@PathVariable String name) {
        String decodedName = URLDecoder.decode(name, StandardCharsets.UTF_8);
        return ResponseEntity.ok(new ResponseDTO<List<EmployeeDTO>>(true, "Filterd with the help of the named query", employeeService.findByProject(name)));
    }

    @GetMapping("/filter/{departmentName}")
    public ResponseEntity<?> filterByDepartName(@PathVariable String departmentName) {
        String decodedName = URLDecoder.decode(departmentName, StandardCharsets.UTF_8);
        return ResponseEntity.ok(new ResponseDTO<List<EmployeeDTO>>(true, "Filter data based on department", employeeService.filterByDepartname(decodedName)));
    }

    @GetMapping("/filter/{start}/{end}")
    public ResponseEntity<?> filterBySalaryRange(@PathVariable int start, @PathVariable int end) {
        return ResponseEntity.ok(new ResponseDTO<List<EmployeeDTO>>(true, "Filtered data based on the salary range", employeeService.filterBySlaryRange(start, end)));
    }

    @PostMapping
    public ResponseEntity<?> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO createdEmployee = employeeService.createEmployee(employeeDTO);
        return ResponseEntity.ok(new ResponseDTO<EmployeeDTO>(true, "Employee added successfully", createdEmployee));
    }

    @PostMapping("/getAll")
    public ResponseEntity<?> getAllEmployees(@RequestBody PageDTO pageDTO) {
        Page<EmployeeEntity> employeeEntityPage = employeeService.getAllEmployees(pageDTO);
        return ResponseEntity.ok(new ResponseDTO<Page<EmployeeEntity>>(true, "Employee fetched successfully", employeeEntityPage));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
        EmployeeDTO employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(new ResponseDTO<EmployeeDTO>(true, "Employee fetched successfully", employee));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDTO employeeDTO) throws EmployeeNotFoundException {
        EmployeeDTO updatedEmployee = employeeService.updateEmployee(id, employeeDTO);
        return ResponseEntity.ok(new ResponseDTO<EmployeeDTO>(true, "Employee updated successfully", updatedEmployee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) throws EmployeeNotFoundException {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok(new ResponseDTO<String>(true, "Employee deleted successfully", "Employee deleted successfully"));
    }

    @GetMapping("/salary/{id}")
    public ResponseEntity<?> calculateSalary(@PathVariable Long id) {
        double salary = employeeService.getSalary(id);
        return ResponseEntity.ok(new ResponseDTO<String>(true, "Salary fetched successfully", "Salary of the employee is:" + salary));
    }
}
