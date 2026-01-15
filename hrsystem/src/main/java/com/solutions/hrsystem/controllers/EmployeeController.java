package com.solutions.hrsystem.controllers;

import com.solutions.hrsystem.entities.Department;
import com.solutions.hrsystem.entities.Employee;
import com.solutions.hrsystem.entities.User;
import com.solutions.hrsystem.repositories.DepartmentRepository;
import com.solutions.hrsystem.repositories.UserRepository;
import com.solutions.hrsystem.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
        try {
            Employee employee = employeeService.getEmployeeById(id);
            return ResponseEntity.ok(employee);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
        try {
            // If user exists, fetch it from database first
            if (employee.getUser() != null && employee.getUser().getUserId() != null) {
                User existingUser = userRepository.findById(employee.getUser().getUserId())
                        .orElseThrow(() -> new RuntimeException("User not found"));
                employee.setUser(existingUser);
            }

            // If department exists, fetch it from database first
            if (employee.getDepartment() != null && employee.getDepartment().getDepartmentId() != null) {
                Department existingDept = departmentRepository.findById(employee.getDepartment().getDepartmentId())
                        .orElseThrow(() -> new RuntimeException("Department not found"));
                employee.setDepartment(existingDept);
            }

            Employee newEmployee = employeeService.createEmployee(employee);
            return ResponseEntity.ok(newEmployee);
        } catch (Exception e) {
            e.printStackTrace(); // Log the error
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        try {
            // If user exists, fetch it from database first
            if (employee.getUser() != null && employee.getUser().getUserId() != null) {
                User existingUser = userRepository.findById(employee.getUser().getUserId())
                        .orElseThrow(() -> new RuntimeException("User not found"));
                employee.setUser(existingUser);
            }

            // If department exists, fetch it from database first
            if (employee.getDepartment() != null && employee.getDepartment().getDepartmentId() != null) {
                Department existingDept = departmentRepository.findById(employee.getDepartment().getDepartmentId())
                        .orElseThrow(() -> new RuntimeException("Department not found"));
                employee.setDepartment(existingDept);
            }

            Employee updatedEmployee = employeeService.updateEmployee(id, employee);
            return ResponseEntity.ok(updatedEmployee);
        } catch (RuntimeException e) {
            e.printStackTrace(); // Log the error
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.ok("Employee deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchEmployees(@RequestParam String keyword) {
        return ResponseEntity.ok(employeeService.searchEmployees(keyword));
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<Employee>> getEmployeesByDepartment(@PathVariable Long departmentId) {
        return ResponseEntity.ok(employeeService.getEmployeesByDepartment(departmentId));
    }
}
