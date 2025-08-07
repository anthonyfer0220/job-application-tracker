package com.anthonyfer0220.job_application_service.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.anthonyfer0220.job_application_service.dto.JobApplicationRequestDTO;
import com.anthonyfer0220.job_application_service.dto.JobApplicationResponseDTO;
import com.anthonyfer0220.job_application_service.exception.JobApplicationNotFoundException;
import com.anthonyfer0220.job_application_service.mapper.JobApplicationMapper;
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

    public JobApplicationResponseDTO getJobApplicationById(UUID id) {
        JobApplication jobApplication = jobApplicationRepository.findById(id)
                .orElseThrow(() -> new JobApplicationNotFoundException("Job application not found with ID: " + id));

        return JobApplicationMapper.toDTO(jobApplication);
    }

    public JobApplicationResponseDTO createJobApplication(JobApplicationRequestDTO jobApplicationRequestDTO) {
        JobApplication newJobApplication = jobApplicationRepository
                .save(JobApplicationMapper.toModel(jobApplicationRequestDTO));

        return JobApplicationMapper.toDTO(newJobApplication);
    }

    public JobApplicationResponseDTO updateJobApplication(UUID id, JobApplicationRequestDTO jobApplicationRequestDTO) {
        jobApplicationRepository.findById(id)
                .orElseThrow(() -> new JobApplicationNotFoundException("Job application not found with ID: " + id));

        JobApplication jobApplication = JobApplicationMapper.toModel(jobApplicationRequestDTO);

        jobApplication.setId(id);

        JobApplication updatedJobApplication = jobApplicationRepository.save(jobApplication);
        return JobApplicationMapper.toDTO(updatedJobApplication);
    }

    public void deleteJobApplication(UUID id) {
        jobApplicationRepository.deleteById(id);
    }
}
