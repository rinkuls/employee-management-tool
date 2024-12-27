package com.employee.management.service;

import com.employee.management.model.Employee;

import java.util.Optional;

public interface EmployeeService {
    public Employee saveEmployeeRecord(Employee employee);

    Optional<Employee> findByName(String name);
}
