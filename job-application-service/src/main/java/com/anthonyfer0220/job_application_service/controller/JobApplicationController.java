package com.anthonyfer0220.job_application_service.controller;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anthonyfer0220.job_application_service.dto.JobApplicationRequestDTO;
import com.anthonyfer0220.job_application_service.dto.JobApplicationResponseDTO;
import com.anthonyfer0220.job_application_service.dto.PageResponseDTO;
import com.anthonyfer0220.job_application_service.service.JobApplicationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/job-applications")
@Tag(name = "Job Application", description = "API for managing Job Applications")
public class JobApplicationController {
    private final JobApplicationService jobApplicationService;

    public JobApplicationController(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    @GetMapping
    @Operation(summary = "Get Job Applications (paginated)")
    public ResponseEntity<PageResponseDTO<JobApplicationResponseDTO>> getJobApplications(
            @RequestHeader("X-User-Email") String userEmail, Pageable pageable) {

        Sort sort = pageable.getSort().isSorted()
                ? pageable.getSort()
                : Sort.by(Sort.Order.desc("dateApplied"));

        Pageable fixed = PageRequest.of(pageable.getPageNumber(), 10, sort);

        PageResponseDTO<JobApplicationResponseDTO> jobApplications = jobApplicationService.getJobApplications(userEmail,
                fixed);

        return ResponseEntity.ok().body(jobApplications);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Job Application")
    public ResponseEntity<JobApplicationResponseDTO> getJobApplicationById(@PathVariable UUID id,
            @RequestHeader("X-User-Email") String userEmail) {

        JobApplicationResponseDTO jobApplicationResponseDTO = jobApplicationService.getJobApplicationById(id,
                userEmail);

        return ResponseEntity.ok().body(jobApplicationResponseDTO);
    }

    @PostMapping
    @Operation(summary = "Create a new Job Application")
    public ResponseEntity<JobApplicationResponseDTO> createJobApplication(
            @Valid @RequestBody JobApplicationRequestDTO jobApplicationRequestDTO,
            @RequestHeader("X-User-Email") String userEmail) {
        JobApplicationResponseDTO jobApplicationResponseDTO = jobApplicationService
                .createJobApplication(jobApplicationRequestDTO, userEmail);

        return ResponseEntity.ok().body(jobApplicationResponseDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Job Application")
    public ResponseEntity<JobApplicationResponseDTO> updateJobApplication(@PathVariable UUID id,
            @Valid @RequestBody JobApplicationRequestDTO jobApplicationRequestDTO,
            @RequestHeader("X-User-Email") String userEmail) {

        JobApplicationResponseDTO jobApplicationResponseDTO = jobApplicationService.updateJobApplication(id,
                jobApplicationRequestDTO, userEmail);

        return ResponseEntity.ok().body(jobApplicationResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Job Application")
    public ResponseEntity<Void> deleteJobApplication(@PathVariable UUID id,
            @RequestHeader("X-User-Email") String userEmail) {
        jobApplicationService.deleteJobApplication(id, userEmail);
        return ResponseEntity.noContent().build();
    }
}
