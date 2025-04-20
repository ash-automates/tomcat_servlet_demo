# Tomcat Docker Demo

This project demonstrates running Apache Tomcat in Docker using docker-compose.

## Prerequisites

- Docker
- Docker Compose

## Setup

1. Clone this repository
2. Place your WAR files in the `webapps` directory
3. Run the container:
   ```bash
   docker-compose up -d
   ```

## Access

Once running, you can access:
- Tomcat manager: http://localhost:8080/manager
- Your deployed applications will be available at: http://localhost:8080/your-app-name

## Directory Structure

- `webapps/`: Directory for WAR file deployment
- `docker-compose.yml`: Docker Compose configuration file

## Configuration

The setup includes:
- Tomcat 10.1 with JDK 17
- Port 8080 exposed for web access
- Volume mount for webapps directory
- 512MB max heap size
- Automatic container restart policy

## Usage

1. Create the webapps directory:
   ```bash
   mkdir webapps
   ```

2. Start the container:
   ```bash
   docker compose up -d
   ```

3. Access Tomcat:
   - Management interface: http://localhost:8080/manager
   - Default page: http://localhost:8080

4. Stop the container:
   ```bash
   docker compose down
   ```

## Notes

- The webapps directory is mounted as a volume, allowing you to deploy applications by copying WAR files into it
- The container will automatically restart unless explicitly stopped
- Tomcat is configured with a maximum heap size of 512MB 