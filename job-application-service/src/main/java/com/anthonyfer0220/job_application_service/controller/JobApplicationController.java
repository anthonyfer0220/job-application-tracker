package com.anthonyfer0220.job_application_service.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anthonyfer0220.job_application_service.dto.JobApplicationRequestDTO;
import com.anthonyfer0220.job_application_service.dto.JobApplicationResponseDTO;
import com.anthonyfer0220.job_application_service.service.JobApplicationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CrossOrigin("*")
@RestController
@RequestMapping("/job-applications")
@Tag(name = "Job Application", description = "API for managing Job Applications")
public class JobApplicationController {
    private final JobApplicationService jobApplicationService;

    public JobApplicationController(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    @GetMapping
    @Operation(summary = "Get Job Applications")
    public ResponseEntity<List<JobApplicationResponseDTO>> getJobApplications() {
        List<JobApplicationResponseDTO> jobApplications = jobApplicationService.getJobApplications();
        return ResponseEntity.ok().body(jobApplications);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Job Application")
    public ResponseEntity<JobApplicationResponseDTO> getJobApplicationById(@PathVariable UUID id) {

        JobApplicationResponseDTO jobApplicationResponseDTO = jobApplicationService.getJobApplicationById(id);

        return ResponseEntity.ok().body(jobApplicationResponseDTO);
    }

    @PostMapping
    @Operation(summary = "Create a new Job Application")
    public ResponseEntity<JobApplicationResponseDTO> createJobApplication(
            @Valid @RequestBody JobApplicationRequestDTO jobApplicationRequestDTO) {
        JobApplicationResponseDTO jobApplicationResponseDTO = jobApplicationService
                .createJobApplication(jobApplicationRequestDTO);

        return ResponseEntity.ok().body(jobApplicationResponseDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Job Application")
    public ResponseEntity<JobApplicationResponseDTO> updateJobApplication(@PathVariable UUID id,
            @Valid @RequestBody JobApplicationRequestDTO jobApplicationRequestDTO) {

        JobApplicationResponseDTO jobApplicationResponseDTO = jobApplicationService.updateJobApplication(id,
                jobApplicationRequestDTO);

        return ResponseEntity.ok().body(jobApplicationResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Job Application")
    public ResponseEntity<Void> deleteJobApplication(@PathVariable UUID id) {
        jobApplicationService.deleteJobApplication(id);
        return ResponseEntity.noContent().build();
    }
}
