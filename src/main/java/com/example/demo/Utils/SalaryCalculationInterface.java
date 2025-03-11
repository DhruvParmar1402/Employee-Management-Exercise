package com.example.demo.Utils;

import com.example.demo.Entity.SalaryEntity;

@FunctionalInterface
public interface SalaryCalculationInterface {
    double calculate(SalaryEntity salaryEntity);
}
