package com.employee.management.controller;

import com.employee.management.model.Employee;
import com.employee.management.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Base64;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class EmployeeRecordAsHTMLController {

    @Autowired
    private final EmployeeService employeeService;

    @Operation(summary = "Get a sample HTML page with a given name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sample HTML page retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Sample page not found")
    })
    @GetMapping("/api/v1/html/sample/{name}")
    @Transactional
    public String GetSample(Model model, @PathVariable String name) {
        model.addAttribute("name", name);
        return "sample"; // Maps to sample.ftl
    }

    @Operation(summary = "Get an employee record as an HTML page by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee record retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @GetMapping("/api/v1/html/employee/{name}")
    @Transactional
    public String GetEmployeeRecord(Model model, @PathVariable String name) {
        Optional<Employee> employeeOpt = employeeService.findByName(name);

        if (employeeOpt.isPresent()) {
            Employee employee = employeeOpt.get();

            // Convert profile picture to Base64 URL
            String profilePictureUrl = null;
            if (employee.getProfilePicture() != null) {
                String base64Image = Base64.getEncoder().encodeToString(employee.getProfilePicture());
                profilePictureUrl = "data:image/jpeg;base64," + base64Image;
            }

            model.addAttribute("employee", employee);
            model.addAttribute("photoUrl", profilePictureUrl);

            return "employee"; // Render employee.ftl
        } else {
            model.addAttribute("errorMessage", "Employee not found.");
            return "error"; // Render error page
        }
    }
}
