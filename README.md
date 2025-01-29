# Task Manager REST API

## Overview

The Task Manager REST API is a Spring Boot-based backend service that provides task management capabilities. 
It integrates with the Trello API, allowing users to import and export tasks between the application and a Trello board. 
Additionally, the system features scheduled email notifications, providing users with regular updates on the number of pending tasks. 
The API is complemented by a frontend developed in JavaScript for a seamless user experience.

## Features

- **Task Management**: Create, update, delete, and retrieve tasks.

- **Trello Integration**: Import tasks from and export tasks to Trello.

- **Scheduled Email Notifications**: Sends periodic emails with task summaries.

- **User Authentication**: Secure access to API endpoints.

- **RESTful API**: Well-structured and documented endpoints for seamless integration.

- **JavaScript Frontend**: A user-friendly interface for interacting with the API.

## Technologies Used

- **Spring Boot**

- **Spting MVC**

- **Spring Data JPA**

- **H2/MySQL**

- **Spring Scheduler**

- **Trello API**

## Installation

**Prerequisites**:

- Java 21+

- Gradle 8.11+

- MySQL or H2 Database

- Trello API Key & Token (For Trello integration)

- SMTP Credentials (For email notifications)


# Setup and Running the Application

1. Clone repository
   ```
   git clone https://github.com/opach16/Tasks-Application.git
   ```
2. Configure the Database  
   Update the `application.properties` file with your MySQL credentials:
   ```
   spring.datasource.url=jdbc:mysql://localhost:3306/task_crud
   spring.datasource.username=<your-database-username>
   spring.datasource.password=<your-database-password>
   ```
3. Add API Keys  
   Include your API data for trello.com in the `application.properties` file:
   ```
   trello.app.key=<your-api-key>
   trello.app.token=<your-api-token>
   trello.app.username=<your-trello-username>
   ```
5. Configure email credentials 
   ```
   spring.mail.host=<your-stmp-server>
   spring.mail.port=<your-stmp-server-port>
   admin.mail=<your-email>
   ```
6. Build and Run the Backend  
   Use Gradle to build and start the backend application:
   ```
   ./gradlew build
   ./gradlew bootRun
   ```
7. Clone the Frontend Repository
   ```
   git clone https://github.com/opach16/opach16.github.io
   ```
8. Set API paths in `script.js` file
   ```
   const apiRoot = '<your-api-path>/v1/tasks';
   const trelloApiRoot = '<your-api-path>/v1/trello';
   ```


# Contact

For any questions or support, contact:

- **Author**: Konrad
- **Email**: opach16@outlook.com



