package com.solutions.hrsystem.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "departments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentId;

    private String departmentName;
    private String description;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;

    private LocalDate createdDate;
}
