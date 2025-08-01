package com.anthonyfer0220.job_application_service.mapper;

import java.time.LocalDate;

import com.anthonyfer0220.job_application_service.dto.JobApplicationRequestDTO;
import com.anthonyfer0220.job_application_service.dto.JobApplicationResponseDTO;
import com.anthonyfer0220.job_application_service.model.FinalDecision;
import com.anthonyfer0220.job_application_service.model.JobApplication;

public class JobApplicationMapper {
    public static JobApplicationResponseDTO toDTO(JobApplication jobApplication) {
        JobApplicationResponseDTO jobApplicationDTO = new JobApplicationResponseDTO();

        jobApplicationDTO.setId(jobApplication.getId().toString());
        jobApplicationDTO.setCompanyName(jobApplication.getCompanyName());
        jobApplicationDTO.setPosition(jobApplication.getPosition());
        jobApplicationDTO.setDateApplied(jobApplication.getDateApplied().toString());
        jobApplicationDTO.setOaDate(jobApplication.getOaDate().toString());
        jobApplicationDTO.setLatestInterviewDate(jobApplication.getLatestInterviewDate().toString());
        jobApplicationDTO.setFinalDecision(jobApplication.getFinalDecision().toString());

        return jobApplicationDTO;
    }

    public static JobApplication toModel(JobApplicationRequestDTO jobApplicationRequestDTO) {
        JobApplication jobApplication = new JobApplication();

        jobApplication.setCompanyName(jobApplicationRequestDTO.getCompanyName());
        jobApplication.setPosition(jobApplicationRequestDTO.getPosition());
        jobApplication.setDateApplied(LocalDate.parse(jobApplicationRequestDTO.getDateApplied()));
        jobApplication.setOaDate(LocalDate.parse(jobApplicationRequestDTO.getOaDate()));
        jobApplication.setLatestInterviewDate(LocalDate.parse(jobApplicationRequestDTO.getLatestInterviewDate()));
        jobApplication.setFinalDecision(FinalDecision.fromString(jobApplicationRequestDTO.getFinalDecision()));

        return jobApplication;
    }
}