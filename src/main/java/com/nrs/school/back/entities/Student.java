package com.nrs.school.back.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Table(name = "STUDENTS")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDT_STUDENT")
    private Long studentId;

    @Column(name = "NAME", nullable = false)
    private String studentName;

    @Column(name = "EMAIL", unique = true, nullable = false)
    private String studentEmail;

    @ManyToOne
    @JoinColumn(name = "CLASSROOM_ID")
    private Classroom classroom;

    @Column(name = "REGISTRATION", length = 7)
    private String registration;

    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public Student() {
    }

    public Student(Long studentId, String studentName, String studentEmail, Classroom classroom, String registration, LocalDateTime createdAt) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.classroom = classroom;
        this.registration = registration;
        this.createdAt = createdAt;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
