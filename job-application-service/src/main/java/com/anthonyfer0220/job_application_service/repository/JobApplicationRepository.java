package com.anthonyfer0220.job_application_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anthonyfer0220.job_application_service.model.JobApplication;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, UUID> {

}
