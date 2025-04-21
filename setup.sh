#!/bin/bash

# Exit immediately if a command exits with a non-zero status
set -e

echo "Starting setup for Inventory Management System..."

# Step 1: Build the application
echo "Building the application with Maven..."
mvn clean package

# Step 2: Ensure Docker is running
if ! docker info > /dev/null 2>&1; then
  echo "Docker is not running. Please start Docker and try again."
  exit 1
fi

# Step 3: Start Docker containers
echo "Starting Docker containers..."
docker compose up -d --build

# Step 4: Wait for MySQL to initialize
echo "Waiting for MySQL to initialize..."
sleep 10

# Step 5: Verify MySQL container is running
if ! docker ps | grep -q "inventory_mysql"; then
  echo "MySQL container is not running. Please check the Docker logs."
  exit 1
fi

# Step 6: Get MySQL container IP and update .env
MYSQL_IP=$(docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' inventory_mysql)
echo "Updating .env with MySQL IP: $MYSQL_IP"
sed -i "s/^MYSQL_HOST=.*/MYSQL_HOST=$MYSQL_IP/" .env

# Step 7: Restart Docker containers to apply changes
echo "Restarting Docker containers..."
docker compose restart

echo "Setup complete! Access the application at http://localhost:8080/"