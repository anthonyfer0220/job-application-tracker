# Job Application Tracker

A full-stack web application to track job applications with CRUD functionality, filtering, and progress tracking.

Built with **Java Spring Boot**, **PostgreSQL**, **React**, and deployed on **AWS** (ECS Fargate, RDS, S3, CloudFront, Route 53, CDK).

Actively used to manage dozens of real applications and designed to scale to hundreds.

## Table of Contents

- [Background](#background)
- [Install](#install)
    - [Prerequisites](#prerequisites)
    - [Installation Steps](#installation-steps)
- [Usage](#usage)
    - [Example](#example)
    - [Notes](#notes)
- [Contributing](#contributing)
- [License](#license)

## Background

During my internship search, I found it difficult to organize and monitor multiple job applications.

This project was built to securely store and track applications, provide progress visibility, and simplify filtering.

It also strengthened my skills in backend engineering, cloud deployment, and infrastructure automation.

### Key Features
- JWT-secured user accounts and APIs
- CRUD operations for job applications
- Flexible filtering across all job fields (company, role, dates, status)
- Production deployment on AWS
- Infrastructure as Code with AWS CDK

## Install

### Prerequisites

This project requires:
- [Git](https://git-scm.com/)
- [Docker](https://www.docker.com/)
- [Node.js](https://nodejs.org)
 
### Installation Steps

1. Clone this repository:
    ```sh
    git clone https://github.com/anthonyfer0220/job-application-tracker.git
    ```

2. Navigate to the project directory:
    ```sh
    cd job-application-tracker
    ```

3. Build and start backend services (Spring Boot API and PostgreSQL) using Docker:
    ```sh
    docker compose up --build
    ```
    The backend API will be available at `http://localhost:4005`

4. Navigate to the frontend directory:
    ```sh
    cd job-application-client
    ```

5. Update the API base URL in the frontend (`job-application-client/src/services/api.js`) to point to the local backend:
    ```javascript
    // change
    baseURL: 'https://api.personaljobtracker.com' 
    // to 
    baseURL: 'http://localhost:4005'
    ```

6. Install and start the frontend:
    ```sh
    npm install
    npm run dev
    ```
    The React app will run at `http://localhost:3000`


## Usage

Once running, you can interact with the application from the browser by reaching `http://localhost:3000`.

### Example

- Create an account and log in.
- Add applications with details such as company, role, and stage.
- Filter by any field (e.g., company, role, application status, or dates) to quickly locate applications.

### Notes

- The live deployment is available at [https://www.personaljobtracker.com](https://www.personaljobtracker.com).
- All cloud infrastructure is defined in the `infrastructure/` folder using AWS CDK.

## Contributing

This is a personal project and is **not open to external contributions**.

Issues may be submitted for reference, but pull requests will not be accepted.

## License

All Rights Reserved © Anthony Fernandez

This code is provided solely for **personal demonstration and portfolio purposes**