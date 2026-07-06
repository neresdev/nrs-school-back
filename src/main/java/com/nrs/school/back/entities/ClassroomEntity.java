package com.nrs.school.back.entities;
import jakarta.persistence.*;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Entity
@Setter
@Table(name = "CLASSROOMS")
public class ClassroomEntity {

    /**
     * Table indentifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_CLASSROOM_IDT")
    @SequenceGenerator(name = "SQ_CLASSROOM_IDT", sequenceName = "SQ_CLASSROOM_IDT", allocationSize = 1)
    @Column(name = "IDT_CLASSROOM")
    private Long id;

    /**
     * Classroom name
     * Ex: 5°A
     */
    @Column(name = "CLASS_NAME", nullable = false)
    private String classroomName;

    /**
     * Maximum students quantity
     */
    @Column(name = "CAPACITY", nullable = false)
    private int capacity;


    /**
     * Teacher name for this classroom
     */
    @Column(name = "TEACHER_NAME", nullable = false)
    private String teacher;

    /**
     * Classroom shift
     * Example: 1 (morning), 2 (evening) or 3 (night)
     */
    @Column(name = "SHIFT", nullable = false)
    private int shift;

    /**
     * Classroom number
     */
    @Column(name = "CLASS_NUMBER", nullable = false)
    private int classNumber;

    @Column(name = "CLASSROOM_REF_CODE")
    private UUID classroomReferenceCode;

    /**
     * Creation date for record
     */
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    /**
     * Update date for record
     */
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public ClassroomEntity() {
    }

    public ClassroomEntity(Long id, String classroomName, int capacity, String teacher, int shift, int classNumber, LocalDateTime createdAt, UUID classroomReferenceCode) {
        this.id = id;
        this.classroomName = classroomName;
        this.capacity = capacity;
        this.teacher = teacher;
        this.shift = shift;
        this.classNumber = classNumber;
        this.createdAt = createdAt;
        this.classroomReferenceCode = classroomReferenceCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    public int getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(int classNumber) {
        this.classNumber = classNumber;
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
