-- Ensure the 'job_application' table exists
CREATE TABLE
    IF NOT EXISTS job_application (
        id UUID PRIMARY KEY,
        company_name VARCHAR(255) NOT NULL,
        position VARCHAR(255) NOT NULL,
        date_applied DATE NOT NULL,
        oa_date DATE,
        latest_interview_date DATE,
        final_decision VARCHAR(50),
        owner_email VARCHAR(255)
    );

-- Insert demo application data with fixed UUIDs (avoids duplicates by checking ID)
INSERT INTO
    job_application (
        id,
        company_name,
        position,
        date_applied,
        oa_date,
        latest_interview_date,
        final_decision,
        owner_email
    )
SELECT
    '123e4567-e89b-12d3-a456-426614174000',
    'Amazon',
    'Software Engineer Intern',
    '2025-01-29',
    '2025-02-12',
    '2025-03-10',
    'PENDING',
    'demo@example.com'
WHERE
    NOT EXISTS (
        SELECT
            1
        FROM
            job_application
        WHERE
            id = '123e4567-e89b-12d3-a456-426614174000'
    );

INSERT INTO
    job_application (
        id,
        company_name,
        position,
        date_applied,
        oa_date,
        latest_interview_date,
        final_decision,
        owner_email
    )
SELECT
    '123e4567-e89b-12d3-a456-426614174001',
    'Google',
    'Platform Intern',
    '2025-02-10',
    '2025-02-20',
    NULL,
    'PENDING',
    'demo@example.com'
WHERE
    NOT EXISTS (
        SELECT
            1
        FROM
            job_application
        WHERE
            id = '123e4567-e89b-12d3-a456-426614174001'
    );

INSERT INTO
    job_application (
        id,
        company_name,
        position,
        date_applied,
        oa_date,
        latest_interview_date,
        final_decision,
        owner_email
    )
SELECT
    '123e4567-e89b-12d3-a456-426614174002',
    'Microsoft',
    'Software Engineer Intern',
    '2025-01-15',
    '2025-01-22',
    '2025-02-12',
    'REJECTED',
    'demo@example.com'
WHERE
    NOT EXISTS (
        SELECT
            1
        FROM
            job_application
        WHERE
            id = '123e4567-e89b-12d3-a456-426614174002'
    );

INSERT INTO
    job_application (
        id,
        company_name,
        position,
        date_applied,
        oa_date,
        latest_interview_date,
        final_decision,
        owner_email
    )
SELECT
    '123e4567-e89b-12d3-a456-426614174003',
    'Netflix',
    'Backend Intern',
    '2025-01-29',
    NULL,
    NULL,
    'PENDING',
    'demo@example.com'
WHERE
    NOT EXISTS (
        SELECT
            1
        FROM
            job_application
        WHERE
            id = '123e4567-e89b-12d3-a456-426614174003'
    );

INSERT INTO
    job_application (
        id,
        company_name,
        position,
        date_applied,
        oa_date,
        latest_interview_date,
        final_decision,
        owner_email
    )
SELECT
    '123e4567-e89b-12d3-a456-426614174004',
    'Datadog',
    'Backend Intern',
    '2025-03-29',
    NULL,
    NULL,
    'PENDING',
    'demo@example.com'
WHERE
    NOT EXISTS (
        SELECT
            1
        FROM
            job_application
        WHERE
            id = '123e4567-e89b-12d3-a456-426614174004'
    );

INSERT INTO
    job_application (
        id,
        company_name,
        position,
        date_applied,
        oa_date,
        latest_interview_date,
        final_decision,
        owner_email
    )
SELECT
    '123e4567-e89b-12d3-a456-426614174005',
    'Snowflake',
    'Software Engineer Intern',
    '2025-02-22',
    '2025-03-11',
    NULL,
    'PENDING',
    'demo@example.com'
WHERE
    NOT EXISTS (
        SELECT
            1
        FROM
            job_application
        WHERE
            id = '123e4567-e89b-12d3-a456-426614174005'
    );

INSERT INTO
    job_application (
        id,
        company_name,
        position,
        date_applied,
        oa_date,
        latest_interview_date,
        final_decision,
        owner_email
    )
SELECT
    '123e4567-e89b-12d3-a456-426614174006',
    'LinkedIn',
    'Software Engineer Intern',
    '2025-02-21',
    '2025-02-23',
    '2025-02-27',
    'REJECTED',
    'demo@example.com'
WHERE
    NOT EXISTS (
        SELECT
            1
        FROM
            job_application
        WHERE
            id = '123e4567-e89b-12d3-a456-426614174006'
    );

INSERT INTO
    job_application (
        id,
        company_name,
        position,
        date_applied,
        oa_date,
        latest_interview_date,
        final_decision,
        owner_email
    )
SELECT
    '123e4567-e89b-12d3-a456-426614174007',
    'Apple',
    'Software Engineer Intern',
    '2025-02-26',
    '2025-03-02',
    '2025-03-26',
    'OFFERED',
    'demo@example.com'
WHERE
    NOT EXISTS (
        SELECT
            1
        FROM
            job_application
        WHERE
            id = '123e4567-e89b-12d3-a456-426614174007'
    );

INSERT INTO
    job_application (
        id,
        company_name,
        position,
        date_applied,
        oa_date,
        latest_interview_date,
        final_decision,
        owner_email
    )
SELECT
    '123e4567-e89b-12d3-a456-426614174008',
    'Meta',
    'Platform Intern',
    '2025-03-11',
    '2025-03-24',
    NULL,
    'PENDING',
    'demo@example.com'
WHERE
    NOT EXISTS (
        SELECT
            1
        FROM
            job_application
        WHERE
            id = '123e4567-e89b-12d3-a456-426614174008'
    );

INSERT INTO
    job_application (
        id,
        company_name,
        position,
        date_applied,
        oa_date,
        latest_interview_date,
        final_decision,
        owner_email
    )
SELECT
    '123e4567-e89b-12d3-a456-426614174009',
    'Chewy',
    'Backend Engineer Intern',
    '2025-02-12',
    '2025-02-22',
    NULL,
    'PENDING',
    'demo@example.com'
WHERE
    NOT EXISTS (
        SELECT
            1
        FROM
            job_application
        WHERE
            id = '123e4567-e89b-12d3-a456-426614174009'
    );

INSERT INTO
    job_application (
        id,
        company_name,
        position,
        date_applied,
        oa_date,
        latest_interview_date,
        final_decision,
        owner_email
    )
SELECT
    '123e4567-e89b-12d3-a456-426614174010',
    'Salesforce',
    'Cloud Engineer Intern',
    '2025-01-28',
    NULL,
    NULL,
    'REJECTED',
    'demo@example.com'
WHERE
    NOT EXISTS (
        SELECT
            1
        FROM
            job_application
        WHERE
            id = '123e4567-e89b-12d3-a456-426614174010'
    );

INSERT INTO
    job_application (
        id,
        company_name,
        position,
        date_applied,
        oa_date,
        latest_interview_date,
        final_decision,
        owner_email
    )
SELECT
    '123e4567-e89b-12d3-a456-426614174011',
    'IBM',
    'Platform Intern',
    '2025-03-15',
    '2025-03-28',
    NULL,
    'PENDING',
    'demo@example.com'
WHERE
    NOT EXISTS (
        SELECT
            1
        FROM
            job_application
        WHERE
            id = '123e4567-e89b-12d3-a456-426614174011'
    );

INSERT INTO
    job_application (
        id,
        company_name,
        position,
        date_applied,
        oa_date,
        latest_interview_date,
        final_decision,
        owner_email
    )
SELECT
    '123e4567-e89b-12d3-a456-426614174012',
    'Tesla',
    'Software Engineer Intern',
    '2025-02-10',
    '2025-03-10',
    NULL,
    'PENDING',
    'demo@example.com'
WHERE
    NOT EXISTS (
        SELECT
            1
        FROM
            job_application
        WHERE
            id = '123e4567-e89b-12d3-a456-426614174012'
    );