-- Create a database
CREATE DATABASE IF NOT EXISTS nrs_school;

-- Use the database
USE nrs_school;

-- Create a table
CREATE TABLE IF NOT EXISTS students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);


