package com.anthonyfer0220.job_application_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobApplicationRequestDTO {
    @NotBlank(message = "Company name is required")
    @Size(max = 100, message = "Company name cannot exceed 100 characters")
    private String companyName;

    @NotBlank(message = "Position is required")
    private String position;

    @NotNull(message = "Date applied is required")
    private String dateApplied;

    private String oaDate;

    private String latestInterviewDate;

    @NotBlank(message = "Final decision is required")
    private String finalDecision;
}