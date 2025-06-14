# Task Management Backend

Spring Boot REST API for managing tasks, users, and projects. Designed for fresher-level training with realistic business logic and Git workflows.

## Features
- User authentication (JWT)
- Role-based authorization (ADMIN, USER)
- CRUD APIs for tasks, users, projects
- Relational DB design (1:N, N:N)
- Exception handling, validation
- Swagger documentation
- Dockerized deployment
- GitHub Actions CI/CD

## Tech Stack
- Java 17, Spring Boot 3
- Spring Security + JWT
- PostgreSQL / MySQL
- Maven / Gradle
- Docker, GitHub Actions

## Run Locally
```bash
git clone https://github.com/viethoangat01/task-management-backend.git
cd task-management-backend
docker-compose up --build
