package com.anthonyfer0220.job_application_service.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anthonyfer0220.job_application_service.dto.JobApplicationRequestDTO;
import com.anthonyfer0220.job_application_service.dto.JobApplicationResponseDTO;
import com.anthonyfer0220.job_application_service.service.JobApplicationService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/job-applications")
public class JobApplicationController {
    private final JobApplicationService jobApplicationService;

    public JobApplicationController(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    @GetMapping
    public ResponseEntity<List<JobApplicationResponseDTO>> getJobApplications() {
        List<JobApplicationResponseDTO> jobApplications = jobApplicationService.getJobApplications();
        return ResponseEntity.ok().body(jobApplications);
    }

    @PostMapping
    public ResponseEntity<JobApplicationResponseDTO> createJobApplication(
            @Valid @RequestBody JobApplicationRequestDTO jobApplicationRequestDTO) {
        JobApplicationResponseDTO jobApplicationResponseDTO = jobApplicationService
                .createJobApplication(jobApplicationRequestDTO);

        return ResponseEntity.ok().body(jobApplicationResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobApplicationResponseDTO> updateJobApplication(@PathVariable UUID id,
            @Valid @RequestBody JobApplicationRequestDTO jobApplicationRequestDTO) {

        JobApplicationResponseDTO jobApplicationResponseDTO = jobApplicationService.updateJobApplication(id,
                jobApplicationRequestDTO);

        return ResponseEntity.ok().body(jobApplicationResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobApplication(@PathVariable UUID id) {
        jobApplicationService.deleteJobApplication(id);
        return ResponseEntity.noContent().build();
    }
}
