package com.anthonyfer0220.job_application_service.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.anthonyfer0220.job_application_service.dto.JobApplicationRequestDTO;
import com.anthonyfer0220.job_application_service.dto.JobApplicationResponseDTO;
import com.anthonyfer0220.job_application_service.exception.JobApplicationNotFoundException;
import com.anthonyfer0220.job_application_service.mapper.JobApplicationMapper;
import com.anthonyfer0220.job_application_service.model.FinalDecision;
import com.anthonyfer0220.job_application_service.model.JobApplication;
import com.anthonyfer0220.job_application_service.repository.JobApplicationRepository;

@Service
public class JobApplicationService {
    private JobApplicationRepository jobApplicationRepository;

    public JobApplicationService(JobApplicationRepository jobApplicationRepository) {
        this.jobApplicationRepository = jobApplicationRepository;
    }

    public List<JobApplicationResponseDTO> getJobApplications(String ownerEmail) {
        List<JobApplication> jobApplications = jobApplicationRepository.findByOwnerEmail(ownerEmail);

        return jobApplications.stream()
                .map(JobApplicationMapper::toDTO).toList();
    }

    public JobApplicationResponseDTO getJobApplicationById(UUID id, String ownerEmail) {
        JobApplication jobApplication = jobApplicationRepository.findByIdAndOwnerEmail(id, ownerEmail)
                .orElseThrow(() -> new JobApplicationNotFoundException(
                        "Job application not found with ID: " + id + " for this user"));

        return JobApplicationMapper.toDTO(jobApplication);
    }

    public JobApplicationResponseDTO createJobApplication(JobApplicationRequestDTO jobApplicationRequestDTO,
            String ownerEmail) {
        JobApplication model = JobApplicationMapper.toModel(jobApplicationRequestDTO);
        model.setOwnerEmail(ownerEmail);

        JobApplication newJobApplication = jobApplicationRepository
                .save(model);

        return JobApplicationMapper.toDTO(newJobApplication);
    }

    public JobApplicationResponseDTO updateJobApplication(UUID id, JobApplicationRequestDTO jobApplicationRequestDTO,
            String ownerEmail) {
        JobApplication jobApplication = jobApplicationRepository.findByIdAndOwnerEmail(id, ownerEmail)
                .orElseThrow(() -> new JobApplicationNotFoundException(
                        "Job application not found with ID: " + id + " for this user"));

        jobApplication.setCompanyName(jobApplicationRequestDTO.getCompanyName());
        jobApplication.setPosition(jobApplicationRequestDTO.getPosition());
        jobApplication.setDateApplied(LocalDate.parse(jobApplicationRequestDTO.getDateApplied()));

        jobApplication.setOaDate(parseOptionalDate(jobApplicationRequestDTO.getOaDate()));
        jobApplication.setLatestInterviewDate(parseOptionalDate(jobApplicationRequestDTO.getLatestInterviewDate()));

        jobApplication.setFinalDecision(FinalDecision.fromString(jobApplicationRequestDTO.getFinalDecision()));

        JobApplication updatedJobApplication = jobApplicationRepository.save(jobApplication);
        return JobApplicationMapper.toDTO(updatedJobApplication);
    }

    public void deleteJobApplication(UUID id, String ownerEmail) {
        JobApplication jobApplication = jobApplicationRepository.findByIdAndOwnerEmail(id, ownerEmail)
                .orElseThrow(() -> new JobApplicationNotFoundException(
                        "Job application not found with ID: " + id + " for this user"));

        jobApplicationRepository.delete(jobApplication);
    }

    private static LocalDate parseOptionalDate(String dateStr) {
        return (dateStr != null && !dateStr.isBlank()) ? LocalDate.parse(dateStr) : null;
    }
}
