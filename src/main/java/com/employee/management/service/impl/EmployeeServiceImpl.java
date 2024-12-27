package com.employee.management.service.impl;

import com.employee.management.model.Employee;
import com.employee.management.repo.EmployeeRepo;
import com.employee.management.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepo employeeRepo;

    @Override
    public Employee saveEmployeeRecord(Employee employee) {

        Optional.ofNullable(employee.getProfessionalDetails())
                .ifPresent(professionalDetails -> professionalDetails.setEmployee(employee));

        Optional.ofNullable(employee.getPastEmployments())
                .ifPresent(pastEmployments ->
                        pastEmployments.forEach(pastEmployment -> pastEmployment.setEmployee(employee)));

        Optional.ofNullable(employee.getKids())
                .ifPresent(kids ->
                        kids.forEach(kid -> kid.setEmployee(employee)));

        Optional.ofNullable(employee.getSpouse())
                .ifPresent(spouse -> spouse.setEmployee(employee));

        return employeeRepo.save(employee);
    }

    @Override
    public Optional<Employee> findByName(String name) {
        return employeeRepo.findByName(name);
    }
}
