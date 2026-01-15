package com.solutions.hrsystem.services;

import com.solutions.hrsystem.entities.Department;
import com.solutions.hrsystem.entities.Employee;
import com.solutions.hrsystem.entities.User;
import com.solutions.hrsystem.repositories.DepartmentRepository;
import com.solutions.hrsystem.repositories.EmployeeRepository;
import com.solutions.hrsystem.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }

    @Transactional
    public Employee createEmployee(Employee employee) {
        // Handle User - fetch existing user by ID
        if (employee.getUser() != null && employee.getUser().getUserId() != null) {
            User existingUser = userRepository.findById(employee.getUser().getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + employee.getUser().getUserId()));
            employee.setUser(existingUser);
        }

        // Handle Department - fetch existing department by ID
        if (employee.getDepartment() != null && employee.getDepartment().getDepartmentId() != null) {
            Department existingDept = departmentRepository.findById(employee.getDepartment().getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found with id: " + employee.getDepartment().getDepartmentId()));
            employee.setDepartment(existingDept);
        }

        return employeeRepository.save(employee);
    }

    @Transactional
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee employee = getEmployeeById(id);

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setPhoneNumber(employeeDetails.getPhoneNumber());
        employee.setPosition(employeeDetails.getPosition());
        employee.setSalary(employeeDetails.getSalary());
        employee.setHireDate(employeeDetails.getHireDate());

        // Handle department update
        if (employeeDetails.getDepartment() != null && employeeDetails.getDepartment().getDepartmentId() != null) {
            Department existingDept = departmentRepository.findById(employeeDetails.getDepartment().getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found with id: " + employeeDetails.getDepartment().getDepartmentId()));
            employee.setDepartment(existingDept);
        }

        return employeeRepository.save(employee);
    }

    @Transactional
    public void deleteEmployee(Long id) {
        Employee employee = getEmployeeById(id);
        employeeRepository.delete(employee);
    }

    public List<Employee> searchEmployees(String keyword) {
        return employeeRepository.findByFirstNameContainingOrLastNameContaining(keyword, keyword);
    }

    public List<Employee> getEmployeesByDepartment(Long departmentId) {
        return employeeRepository.findByDepartment_DepartmentId(departmentId);
    }

    public List<Employee> getEmployeesByPosition(String position) {
        return employeeRepository.findByPosition(position);
    }
}
