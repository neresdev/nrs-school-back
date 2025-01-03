CREATE DATABASE IF NOT EXISTS NRS_SCHOOL;

USE NRS_SCHOOL;

CREATE TABLE IF NOT EXISTS CLASSROOMS (
    IDT_CLASSROOM BIGINT AUTO_INCREMENT PRIMARY KEY,
    CLASS VARCHAR(3) NOT NULL,
    CAPACITY TINYINT NOT NULL,
    TEACHER VARCHAR(255) NOT NULL,
    SHIFT TINYINT NOT NULL,
    CLASS_NUMBER TINYINT NOT NULL,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS STUDENTS (
    IDT_STUDENT BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL,
    EMAIL VARCHAR(255) NOT NULL,
    CLASSROOM_ID BIGINT,
    REGISTRATION VARCHAR(7) UNIQUE,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE(IDT_STUDENT, EMAIL, REGISTRATION)

);
