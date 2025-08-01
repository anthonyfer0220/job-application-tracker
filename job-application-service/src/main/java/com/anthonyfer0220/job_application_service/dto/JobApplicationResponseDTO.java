package com.anthonyfer0220.job_application_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobApplicationResponseDTO {
    private String id;
    private String companyName;
    private String position;
    private String dateApplied;
    private String oaDate;
    private String latestInterviewDate;
    private String finalDecision;
}
