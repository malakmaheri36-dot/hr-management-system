package com.solutions.hrsystem;

import com.solutions.hrsystem.entities.*;
import com.solutions.hrsystem.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TeamRepository teamRepository;
    private final PasswordEncoder passwordEncoder;  // ADD THIS

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() > 0) {
            System.out.println("ℹ️ Database already contains data. Skipping initialization.");
            return;
        }

        // Create Admin User
        User adminUser = new User();
        adminUser.setUsername("admin");
        adminUser.setPassword(passwordEncoder.encode("admin123"));  // ENCODE PASSWORD
        adminUser.setEmail("admin@hrms.com");
        adminUser.setUserType("ADMIN");
        userRepository.save(adminUser);

        // Create Employee User
        User empUser = new User();
        empUser.setUsername("malak");  // CHANGED from malak.maheri
        empUser.setPassword(passwordEncoder.encode("malak123"));  // ENCODE PASSWORD
        empUser.setEmail("malak.maheri@company.com");
        empUser.setUserType("EMPLOYEE");
        userRepository.save(empUser);

        // Create HR Manager User
        User hrUser = new User();
        hrUser.setUsername("hr.manager");
        hrUser.setPassword(passwordEncoder.encode("hr123"));  // ENCODE PASSWORD
        hrUser.setEmail("hr@hrms.com");
        hrUser.setUserType("HR_MANAGER");
        userRepository.save(hrUser);

        // Create Department
        Department itDept = new Department();
        itDept.setDepartmentName("IT Department");
        itDept.setDescription("Information Technology and Software Development");
        departmentRepository.save(itDept);

        Department hrDept = new Department();
        hrDept.setDepartmentName("Human Resources");
        hrDept.setDescription("Employee management and recruitment");
        departmentRepository.save(hrDept);

        // Create Employee
        Employee employee = new Employee();
        employee.setUser(empUser);
        employee.setFirstName("Malak");
        employee.setLastName("Maheri");
        employee.setPhoneNumber("0661234567");
        employee.setPosition("Junior Software Developer");
        employee.setSalary(30000.0);
        employee.setHireDate(LocalDate.of(2025, 12, 1));
        employee.setDepartment(itDept);
        employeeRepository.save(employee);

        // Create HR Manager Employee
        Employee hrManager = new Employee();
        hrManager.setUser(hrUser);
        hrManager.setFirstName("Sarah");
        hrManager.setLastName("Johnson");
        hrManager.setPhoneNumber("0667654321");
        hrManager.setPosition("HR Manager");
        hrManager.setSalary(50000.0);
        hrManager.setHireDate(LocalDate.of(2024, 1, 15));
        hrManager.setDepartment(hrDept);
        employeeRepository.save(hrManager);

        // Create Teams
        Team devTeam = new Team();
        devTeam.setName("Development Team");
        devTeam.setDescription("Full-stack development team");
        devTeam.setDepartment(itDept);
        teamRepository.save(devTeam);

        Team qaTeam = new Team();
        qaTeam.setName("QA Team");
        qaTeam.setDescription("Quality Assurance and Testing");
        qaTeam.setDepartment(itDept);
        teamRepository.save(qaTeam);

        Team recruitmentTeam = new Team();
        recruitmentTeam.setName("Recruitment Team");
        recruitmentTeam.setDescription("Talent acquisition and onboarding");
        recruitmentTeam.setDepartment(hrDept);
        teamRepository.save(recruitmentTeam);

        // Create Projects
        Project webProject = new Project();
        webProject.setProjectName("HR Management System");
        webProject.setDescription("Full-stack web application for employee management");
        webProject.setStartDate(LocalDate.of(2025, 12, 15));
        webProject.setEndDate(LocalDate.of(2026, 6, 15));
        webProject.setStatus("IN_PROGRESS");
        webProject.setProgress(45.0);
        webProject.setBudget(75000.0);
        webProject.setManager(employee);
        webProject.setDepartment(itDept);
        projectRepository.save(webProject);

        Project mobileProject = new Project();
        mobileProject.setProjectName("Mobile App Development");
        mobileProject.setDescription("Cross-platform mobile application using Flutter");
        mobileProject.setStartDate(LocalDate.of(2026, 1, 5));
        mobileProject.setEndDate(LocalDate.of(2026, 7, 5));
        mobileProject.setStatus("IN_PROGRESS");
        mobileProject.setProgress(20.0);
        mobileProject.setBudget(120000.0);
        mobileProject.setManager(employee);
        mobileProject.setDepartment(itDept);
        projectRepository.save(mobileProject);

        Project hrSystemProject = new Project();
        hrSystemProject.setProjectName("Employee Onboarding Portal");
        hrSystemProject.setDescription("Streamlined onboarding process for new hires");
        hrSystemProject.setStartDate(LocalDate.of(2026, 1, 10));
        hrSystemProject.setEndDate(LocalDate.of(2026, 4, 10));
        hrSystemProject.setStatus("PLANNING");
        hrSystemProject.setProgress(5.0);
        hrSystemProject.setBudget(45000.0);
        hrSystemProject.setManager(hrManager);
        hrSystemProject.setDepartment(hrDept);
        projectRepository.save(hrSystemProject);

        // Create Task
        Task task = new Task();
        task.setTaskName("Complete HR System Backend");
        task.setDescription("Develop Spring Boot REST API");
        task.setDueDate(LocalDate.now().plusDays(7));
        task.setPriority("HIGH");
        task.setStatus("IN_PROGRESS");
        task.setAssignedEmployee(employee);
        taskRepository.save(task);

        System.out.println("✅ Data initialized with ENCRYPTED passwords!");
        System.out.println("🔐 Login credentials:");
        System.out.println("   Admin: admin / admin123");
        System.out.println("   Employee: malak / malak123");
        System.out.println("   HR Manager: hr.manager / hr123");
    }
}
