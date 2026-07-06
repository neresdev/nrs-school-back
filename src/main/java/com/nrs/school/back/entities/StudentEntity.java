package com.nrs.school.back.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "STUDENTS")
public class StudentEntity {

    /**
     * Table identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_STUDENT_IDT")
    @SequenceGenerator(name = "SQ_STUDENT_IDT", sequenceName = "SQ_STUDENT_IDT", allocationSize = 1)
    @Column(name = "IDT_STUDENT", nullable = false, unique = true)
    private Long studentId;

    /**
     * Student name
     */
    @Column(name = "NAME", nullable = false)
    private String studentName;

    /**
     * Student email
     */
    @Column(name = "EMAIL", nullable = false, unique = true)
    private String studentEmail;

    /**
     * Classrooms table identifier
     */
    @Column(name = "IDT_CLASSROOM")
    private Long classroomId;

    /**
     * Student registration
     */
    @Column(name = "REGISTRATION", unique = true, length = 7)
    private String registration;

    @Column(name = "STUDENT_REF_CODE", nullable = false)
    private UUID studentReferenceCode;

    @Column(name = "CREATED_AT", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public StudentEntity() {
    }

    public StudentEntity(String studentName, String studentEmail, String registration) {
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.registration = registration;
        this.studentReferenceCode = UUID.randomUUID();
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

    public Long getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
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

    public UUID getStudentReferenceCode() {
        return studentReferenceCode;
    }

    public void setStudentReferenceCode(UUID studentReferenceCode) {
        this.studentReferenceCode = studentReferenceCode;
    }
}
