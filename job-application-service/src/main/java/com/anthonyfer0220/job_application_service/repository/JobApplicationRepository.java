package com.anthonyfer0220.job_application_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anthonyfer0220.job_application_service.model.JobApplication;
import java.util.List;
import java.util.Optional;


@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, UUID> {
    List<JobApplication> findByOwnerEmail(String ownerEmail);
    Optional<JobApplication> findByIdAndOwnerEmail(UUID id, String ownerEmail);
}
