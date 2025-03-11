package com.example.demo.Service.Employee;

import com.example.demo.DTO.EmployeeDTO;
import com.example.demo.DTO.PageDTO;
import com.example.demo.DTO.SalaryDTO;
import com.example.demo.Entity.DepartmentEntity;
import com.example.demo.Entity.EmployeeEntity;
import com.example.demo.Entity.ProjectEntity;
import com.example.demo.Entity.SalaryEntity;
import com.example.demo.Exceptions.EmployeeNotFoundException;
import com.example.demo.Repository.DepartmentRepository;
import com.example.demo.Repository.EmployeeRepository;
import com.example.demo.Repository.ProjectRepository;
import com.example.demo.Repository.SalaryRepository;
import com.example.demo.Utils.SalaryCalculationInterface;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class EmployeeService implements EmployeeInterface {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private SalaryRepository salaryRepository;

    @Autowired
    private ProjectRepository projectRepository;


    public List<EmployeeDTO> filterByDepartname(String decodedName) {
        List<EmployeeEntity> employeeEntity = employeeRepository.findAll();
        List<EmployeeEntity> filteredData = employeeEntity.stream().filter((employeeEnt) -> decodedName.equals(employeeEnt.getDepartmentEntity().getDepartmentName())).collect(Collectors.toList());
        List<EmployeeDTO> employeeDTOs = filteredData.stream()
                .map(EmployeeService::fromEntity)
                .collect(Collectors.toList());
        return employeeDTOs;
    }

    public List<EmployeeDTO> filterBySlaryRange(int start, int end) {
        List<EmployeeEntity> employeeEntity = employeeRepository.findAll().stream().filter((ent) -> ent.getSalaryEntity().getBasicSalary() > start && ent.getSalaryEntity().getBasicSalary() < end).collect(Collectors.toUnmodifiableList());
        List<EmployeeDTO> filteredDTO = employeeEntity.stream().map(EmployeeService::fromEntity).collect(Collectors.toUnmodifiableList());
        return filteredDTO;
    }

    public List<EmployeeDTO> filterByQuery(String name) {
        return employeeRepository.findByDepartment(name).stream().map(EmployeeService::fromEntity).collect(Collectors.toUnmodifiableList());
    }

    public List<EmployeeDTO> findBySalaryGreaterThan(int amount) {
        return employeeRepository.findBySalaryGreaterThan(amount).stream().map(EmployeeService::fromEntity).collect(Collectors.toUnmodifiableList());
    }

    public List<EmployeeDTO> findByProject(String name) {
        return employeeRepository.findByProject(name).stream().map(EmployeeService::fromEntity).collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        EmployeeEntity employee = new EmployeeEntity();
        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setAddress(employeeDTO.getAddress());
        employee.setGender(employeeDTO.getGender());
        employee.setOccupation(employeeDTO.getOccupation());

        DepartmentEntity department = departmentRepository.findById(employeeDTO.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException("Department not found"));
        employee.setDepartmentEntity(department);

        SalaryDTO salaryDTO = employeeDTO.getSalaryId();
        SalaryEntity salaryEntity = salaryRepository.save(new SalaryEntity(salaryDTO.getBasicSalary(), salaryDTO.getHra(), salaryDTO.getBonus(), employee));
        employee.setSalaryEntity(salaryEntity);

        List<ProjectEntity> projects = employeeDTO.getProjectIds().stream()
                .map(id -> projectRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Project not found: " + id)))
                .collect(Collectors.toList());
        employee.setProjectsEntity(projects);

        employeeRepository.save(employee);
        return fromEntity(employee);
    }

    public Page<EmployeeEntity> getAllEmployees(PageDTO pageDTO) {
        Sort sort = "desc".equalsIgnoreCase(pageDTO.getOrder()) ? Sort.by(pageDTO.getColumnName()).descending() : Sort.by(pageDTO.getColumnName()).ascending();
        Pageable pageable = PageRequest.of(pageDTO.getOffset(), pageDTO.getSize(), sort);
        Page<EmployeeEntity> employeeEntityPage = employeeRepository.findAll(pageable);
//        Page<EmployeeDTO> employeeDTOPage=mapEntityPageToDTOPage(employeeEntityPage);
        return employeeEntityPage;
    }

//    private Page<EmployeeDTO> mapEntityPageToDTOPage(Page<EmployeeEntity> employeeEntityPage) {
//
//        employeeEntityPage.map(()->{
//
//        });
//    }

    public EmployeeDTO getEmployeeById(Long id) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        return fromEntity(employeeEntity);
    }

    @Transactional
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) throws EmployeeNotFoundException {
        EmployeeEntity employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setAddress(employeeDTO.getAddress());
        employee.setGender(employeeDTO.getGender());
        employee.setOccupation(employeeDTO.getOccupation());

        DepartmentEntity department = departmentRepository.findById(employeeDTO.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException("Department not found"));
        employee.setDepartmentEntity(department);

        SalaryEntity salary = salaryRepository.findById((long) employee.getSalaryEntity().getId())
                .orElseThrow(() -> new EntityNotFoundException("Salary not found"));

        salary.setBasicSalary(employeeDTO.getSalaryId().getBasicSalary());
        salary.setHra((employeeDTO.getSalaryId().getHra()));
        ;
        salary.setBonus(employeeDTO.getSalaryId().getBonus());

        employee.setSalaryEntity(salary);

        List<ProjectEntity> projects = employeeDTO.getProjectIds().stream()
                .map(projectNames -> projectRepository.findById(projectNames)
                        .orElseThrow(() -> new EntityNotFoundException("Project not found: " + projectNames)))
                .collect(Collectors.toList());
        employee.setProjectsEntity(projects);

        employeeRepository.save(employee);
        return fromEntity(employee);
    }

    public void deleteEmployee(Long id) throws EmployeeNotFoundException {
        EmployeeEntity employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));
        employeeRepository.delete(employee);
    }

    public double getSalary(Long id) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Given employee does not exists"));
        if (employeeEntity.getSalaryEntity() == null) {
            throw new EntityNotFoundException("Salary for this employee does not exists");
        }
        SalaryCalculationInterface calculationInterface = (salaryEntity) -> {
            return salaryEntity.getBonus() + salaryEntity.getBasicSalary() + salaryEntity.getHra();
        };
        return calculationInterface.calculate(employeeEntity.getSalaryEntity());
    }

    public static EmployeeDTO fromEntity(EmployeeEntity entity) {
        return new EmployeeDTO(
                (long) entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getAddress(),
                entity.getGender(),
                entity.getOccupation(),
                entity.getDepartmentEntity().getDepartmentName(),
                entity.getSalaryEntity() != null ? new SalaryDTO(
                        (long) entity.getSalaryEntity().getId(),
                        entity.getSalaryEntity().getBasicSalary(),
                        entity.getSalaryEntity().getHra(),
                        entity.getSalaryEntity().getBonus()
                ) : null,
                entity.getProjectsEntity().stream()
                        .map(ProjectEntity::getProjectName)
                        .collect(Collectors.toList())
        );
    }
}
