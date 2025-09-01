package com.anthonyfer0220.job_application_service.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anthonyfer0220.job_application_service.model.JobApplication;
import java.util.Optional;


@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, UUID> {
    Page<JobApplication> findByOwnerEmail(String ownerEmail, Pageable pageable);
    Optional<JobApplication> findByIdAndOwnerEmail(UUID id, String ownerEmail);
}
