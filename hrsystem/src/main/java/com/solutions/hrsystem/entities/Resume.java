package com.solutions.hrsystem.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "resumes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resumeId;

    private String skills;
    private String education;
    private String experience;
    private String certifications;
    private LocalDateTime lastUpdated;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
