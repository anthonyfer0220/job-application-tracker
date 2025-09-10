-- Ensure the 'users' table exists
CREATE TABLE
    IF NOT EXISTS "users" (
        id UUID PRIMARY KEY,
        email VARCHAR(255) UNIQUE NOT NULL,
        password VARCHAR(255) NOT NULL
    );

-- Insert a demo user only if they are not already present (by id or email)
INSERT INTO
    "users" (id, email, password)
SELECT
    '223e4567-e89b-12d3-a456-426614174006',
    'demo@example.com',
    '$2y$10$jNAM8UNS.qOqtnK5lTGIt.JxCAC6NUwnqNRnhAxlLpqjPHaJG9h5y'
WHERE
    NOT EXISTS (
        SELECT
            1
        FROM
            "users"
        WHERE
            id = '223e4567-e89b-12d3-a456-426614174006'
            OR email = 'demo@example.com'
    )