package com.employee.management.repo;

import com.employee.management.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {

    Optional<Employee> findByName(String name);
    Optional<Employee> findByEmpId(Long empId);


}
