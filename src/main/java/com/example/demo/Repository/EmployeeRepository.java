package com.example.demo.Repository;

import com.example.demo.Entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    @Query(name = "EmployeeEntity.findByDepartment")
    List<EmployeeEntity> findByDepartment(@Param("deptName") String deptName);

    @Query(name = "EmployeeEntity.findBySalaryGreaterThan")
    List<EmployeeEntity> findBySalaryGreaterThan(@Param("salary")int salary);

    @Query(name = "EmployeeEntity.findByProject")
    List<EmployeeEntity> findByProject (@Param("projectName") String projectName);
}
