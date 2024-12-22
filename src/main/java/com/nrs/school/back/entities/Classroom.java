package com.nrs.school.back.entities;
import jakarta.persistence.*;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Setter
@Table(name = "CLASSROOMS")
public class Classroom {

    /**
     * Table indentifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_CLASSROOM_IDT")
    @Column(name = "IDT_CLASSROOM")
    private Long id;

    /**
     * Classroom name
     * Ex: 5°A
     */
    @Column(name = "CLASS", nullable = false)
    private String className;

    /**
     * Maximum students quantity
     */
    @Column(name = "CAPACITY", nullable = false)
    private int capacity;


    /**
     * Teacher name for this classroom
     */
    @Column(name = "TEACHER", nullable = false)
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

    public Classroom() {
    }

    public Classroom(Long id, String className, int capacity, String teacher, int shift, int classNumber, LocalDateTime createdAt) {
        this.id = id;
        this.className = className;
        this.capacity = capacity;
        this.teacher = teacher;
        this.shift = shift;
        this.classNumber = classNumber;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
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
