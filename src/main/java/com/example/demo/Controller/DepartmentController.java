package com.example.demo.Controller;

import com.example.demo.DTO.DepartmentDTO;
import com.example.demo.DTO.ResponseDTO;
import com.example.demo.Service.Department.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<?> createDepartment(@Valid @RequestBody DepartmentDTO departmentDTO) {
        return ResponseEntity.ok(new ResponseDTO<DepartmentDTO>(true, "Department created successfully", departmentService.createDepartment(departmentDTO)));
    }

    @GetMapping
    public ResponseEntity<?> getAllDepartments() {
        return ResponseEntity.ok(new ResponseDTO<List<DepartmentDTO>>(true, "Department fetched successfully", departmentService.getAllDepartments()));
    }

    @GetMapping("/{departmentName}")
    public ResponseEntity<?> getDepartment(@PathVariable String departmentName) {
        String decodedName = URLDecoder.decode(departmentName, StandardCharsets.UTF_8);
        return ResponseEntity.ok(new ResponseDTO<DepartmentDTO>(true, "Department fetched successfully", departmentService.getDepartmentByName(decodedName)));
    }

    @PutMapping("/{departmentName}")
    public ResponseEntity<?> updateDepartment(@PathVariable String departmentName, @Valid @RequestBody DepartmentDTO departmentDTO) {
        String decodedName = URLDecoder.decode(departmentName, StandardCharsets.UTF_8);
        return ResponseEntity.ok(new ResponseDTO<DepartmentDTO>(true, "Department updated successfully", departmentService.updateDepartment(departmentName, departmentDTO)));
    }

    @DeleteMapping("/{departmentName}")
    public ResponseEntity<?> deleteDepartment(@PathVariable String departmentName) {
        departmentService.deleteDepartment(departmentName);
        return ResponseEntity.ok(new ResponseDTO<>(true, "Department deleted successfully", null));
    }
}
