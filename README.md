# Empik Recruitment

## Description
Simple Spring Boot RESTful API that provides information about GitHub users based on their login names.

Service was implemented to fulfill the following requirements:
- Retrieve user data from GitHub API.
- Expose the following user information:
  - Identifier
  - Login
  - Name
  - Type
  - Avatar URL
  - Creation Date
  - Calculations based on followers and public repositories
- Store the number of API requests made for each login in a database.
## Database
Service uses an H2 in-memory database to store the number of API requests made for each login. Database schema and data initialization are handled using Liquibase scripts.
## API Documentation
API documentation is available using Swagger. Access the Swagger UI at http://localhost:8080/swagger-ui.html.
