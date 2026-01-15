package com.solutions.hrsystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String position;
    private Double salary;
    private LocalDate hireDate;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToMany
    @JsonIgnore
    private List<Team> teams;

    @OneToMany(mappedBy = "assignedEmployee")
    @JsonIgnore
    private List<Task> tasks;

    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    private List<CalendarEvent> calendarEvents;

    @OneToOne(mappedBy = "employee")
    @JsonIgnore
    private Resume resume;
}
