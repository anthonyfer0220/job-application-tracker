package com.anthonyfer0220.job_application_service.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @Column(nullable = false)
    private String companyName;

    @NotNull
    @Column(nullable = false)
    private String position;

    @NotNull
    @Column(nullable = false)
    private LocalDate dateApplied;

    private LocalDate oaDate;

    private LocalDate latestInterviewDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FinalDecision finalDecision;
}
