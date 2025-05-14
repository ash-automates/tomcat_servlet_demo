# Inventory Management System - macOS Setup Guide

This guide provides instructions for setting up and running the Inventory Management System on macOS.

## Prerequisites

1. **Install Java Development Kit (JDK) 17**
   ```bash
   brew install openjdk@17
   ```
   Add Java to your PATH:
   ```bash
   echo 'export PATH="/usr/local/opt/openjdk@17/bin:$PATH"' >> ~/.zshrc
   source ~/.zshrc
   ```

2. **Install Maven**
   ```bash
   brew install maven
   ```

3. **Install Docker Desktop for Mac**
   - Download Docker Desktop from [https://www.docker.com/products/docker-desktop/](https://www.docker.com/products/docker-desktop/)
   - Install and start Docker Desktop

## Building the Application

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd tomcat_servlet_demo
   ```

2. **Build the application using Maven**
   ```bash
   mvn clean package
   ```
   This will create a WAR file in the `target` directory named `inventory-1.0-SNAPSHOT.war`.

## Running the Application

1. **Start the containers using Docker Compose**
   ```bash
   docker-compose up -d
   ```

   This will:
   - Start a MySQL database container with the required setup
   - Start a Tomcat container with the application deployed
   - The containers will communicate with each other over a Docker network

2. **Access the application**
   - Open your browser and navigate to [http://localhost:8080/](http://localhost:8080/)
   - Login with username: `admin` and password: `admin123`

## Working with the Application

The system now includes a multi-criteria search feature that allows you to search inventory items by:
- Description
- Price range
- Expiration date range
- Quantity range

## Debugging

1. **View container logs**
   ```bash
   # View Tomcat logs
   docker logs tomcat_container
   
   # View MySQL logs
   docker logs mysql_container
   ```

2. **Access the MySQL database**
   ```bash
   docker exec -it mysql_container mysql -uinventory_user -pinventory_password inventory_db
   ```

3. **Connect to the Tomcat container**
   ```bash
   docker exec -it tomcat_container /bin/bash
   ```

## Stopping the Application

```bash
docker-compose down
```

To remove the volumes as well (this will delete all data):
```bash
docker-compose down -v
```

## Development Tips for macOS

1. **MySQL Client**
   - For a GUI MySQL client, consider using Sequel Pro or TablePlus
   ```bash
   brew install --cask sequel-pro
   # OR
   brew install --cask tableplus
   ```

2. **Java IDE**
   - For Java development on macOS, consider IntelliJ IDEA or Visual Studio Code
   ```bash
   brew install --cask intellij-idea-ce
   # OR
   brew install --cask visual-studio-code
   ```

3. **Hot Reload**
   - For development with hot reload, modify the docker-compose.yml to mount your source directory directly

## Troubleshooting macOS-specific Issues

1. **Port conflicts**
   - If port 8080 or 3306 is already in use:
     ```bash
     # Check what's using the port
     lsof -i :8080
     lsof -i :3306
     
     # Change the port mapping in docker-compose.yml
     ```

2. **Docker performance on macOS**
   - Increase Docker Desktop resources in Preferences > Resources
   - Consider using volume delegated mode for better performance:
     ```yaml
     volumes:
       - ./target/inventory-1.0-SNAPSHOT.war:/usr/local/tomcat/webapps/ROOT.war:delegated
     ```

3. **File permission issues**
   - If you encounter file permission problems, consider using explicit user mappings in your Docker Compose file.
