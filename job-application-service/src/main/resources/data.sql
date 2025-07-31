-- Ensure the 'job_application' table exists
CREATE TABLE IF NOT EXISTS job_application
(
    id              UUID PRIMARY KEY,
    company_name    VARCHAR(255)        NOT NULL,
    position           VARCHAR(255)  NOT NULL,
    date_applied         DATE        NOT NULL,
    oa_date   DATE,
    latest_interview_date DATE,
    final_decision VARCHAR(50)
);

-- Insert well-known UUIDs for specific job_applications
INSERT INTO job_application (id, company_name, position, date_applied, oa_date, latest_interview_date, final_decision)
SELECT '123e4567-e89b-12d3-a456-426614174000',
       'Amazon',
       'Software Engineer Intern',
       '2025-01-29',
       '2025-02-12',
       '2025-03-10',
       'PENDING'
WHERE NOT EXISTS (SELECT 1
                  FROM job_application
                  WHERE id = '123e4567-e89b-12d3-a456-426614174000');