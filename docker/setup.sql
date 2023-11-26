CREATE DATABASE IF NOT EXISTS nrs_school;

USE nrs_school;

CREATE TABLE IF NOT EXISTS students (
    id INT AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    registration varchar(7) UNIQUE,
    PRIMARY KEY (id)

);