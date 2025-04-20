# Inventory Management System

A simple inventory management system built with Java Servlets, JSP, and MySQL.

## Prerequisites

- Docker Desktop (Windows/Mac) or Docker Engine + Docker Compose (Linux)
- Java Development Kit (JDK) 17
- Maven
- Git

## Setup Instructions

### Windows Setup
1. Install prerequisites:
   - Download and install [Docker Desktop](https://www.docker.com/products/docker-desktop/)
   - Install [JDK 17](https://adoptium.net/)
   - Install [Maven](https://maven.apache.org/download.cgi)
   - Make sure Docker Desktop is running before proceeding

2. Clone the repository:
   ```powershell
   git clone <repository-url>
   cd <repository-name>
   ```

3. Get the `.env` file from your team member and place it in the project root directory.

4. Build and run (in PowerShell or Command Prompt):
   ```powershell
   mvn clean package
   docker compose up -d --build
   ```

### Linux/Mac Setup
1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd <repository-name>
   ```

2. Get the `.env` file from your team member and place it in the project root directory.

3. Build and run:
   ```bash
   mvn clean package
   docker-compose up -d --build
   ```

### Access the Application

Once running, access the application at:
- URL: http://localhost:8080/inventory
- Default credentials:
  - Username: admin
  - Password: admin123

## Development

- The application uses Tomcat 10.1 with Jakarta EE 10
- MySQL 8.0 is used as the database
- Source code is in `src/main/java/com/inventory`
- JSP files are in `src/main/webapp`
- Database initialization script is in `docker-entrypoint-initdb.d/init.sql`

## Troubleshooting

If you encounter any issues:

1. Check the logs:
   ```bash
   # Windows (PowerShell/Command Prompt)
   docker compose logs -f

   # Linux/Mac
   docker-compose logs -f
   ```

2. Rebuild the containers:
   ```bash
   # Windows (PowerShell/Command Prompt)
   docker compose down
   mvn clean package
   docker compose up -d --build

   # Linux/Mac
   docker-compose down
   mvn clean package
   docker-compose up -d --build
   ```

3. If database changes are not reflecting:
   ```bash
   # Windows (PowerShell/Command Prompt)
   docker compose down -v
   docker compose up -d --build

   # Linux/Mac
   docker-compose down -v
   docker-compose up -d --build
   ```

### Common Windows Issues

1. If you see "command not found":
   - Make sure Docker Desktop is running
   - Make sure you've added Maven and Java to your system PATH

2. If ports are already in use:
   - Check if any other applications are using ports 8080 or 3306
   - You can stop them or modify the ports in docker-compose.yml

3. Line ending issues:
   - If you see strange errors in shell scripts, run:
     ```powershell
     git config --global core.autocrlf false
     ```
   - Then clone the repository again 