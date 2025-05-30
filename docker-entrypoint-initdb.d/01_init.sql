-- Create and grant privileges to the inventory_user
CREATE USER IF NOT EXISTS 'inventory_user'@'%' IDENTIFIED BY 'inventory_password';
GRANT ALL PRIVILEGES ON inventory_db.* TO 'inventory_user'@'%';
FLUSH PRIVILEGES;

USE inventory_db;

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL,
    expirationDate DATE NOT NULL,
    quantity INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Insert a default admin user (password: admin123)
INSERT INTO users (username, password) VALUES ('admin', 'admin123');

-- Show tables and their structure
SHOW TABLES;
DESCRIBE users;
DESCRIBE items;