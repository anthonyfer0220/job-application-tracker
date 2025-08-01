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

    public List<JobApplicationResponseDTO> getJobApplications() {
        List<JobApplication> jobApplications = jobApplicationRepository.findAll();

        return jobApplications.stream()
                .map(JobApplicationMapper::toDTO).toList();
    }

    public JobApplicationResponseDTO createJobApplication(JobApplicationRequestDTO jobApplicationRequestDTO) {
        JobApplication newJobApplication = jobApplicationRepository
                .save(JobApplicationMapper.toModel(jobApplicationRequestDTO));

        return JobApplicationMapper.toDTO(newJobApplication);
    }

    public JobApplicationResponseDTO updateJobApplication(UUID id, JobApplicationRequestDTO jobApplicationRequestDTO) {
        JobApplication jobApplication = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new JobApplicationNotFoundException("Job application not found with ID: " + id));

        jobApplication.setCompanyName(jobApplicationRequestDTO.getCompanyName());
        jobApplication.setPosition(jobApplicationRequestDTO.getPosition());
        jobApplication.setDateApplied(LocalDate.parse(jobApplicationRequestDTO.getDateApplied()));
        jobApplication.setOaDate(LocalDate.parse(jobApplicationRequestDTO.getOaDate()));
        jobApplication.setLatestInterviewDate(LocalDate.parse(jobApplicationRequestDTO.getLatestInterviewDate()));
        jobApplication.setFinalDecision(FinalDecision.fromString(jobApplicationRequestDTO.getFinalDecision()));

        JobApplication updatedJobApplication = jobApplicationRepository.save(jobApplication);
        return JobApplicationMapper.toDTO(updatedJobApplication);
    }

    public void deleteJobApplication(UUID id) {
        jobApplicationRepository.deleteById(id);
    }
}
