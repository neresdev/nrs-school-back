package com.nrs.school.back.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "STUDENTS_RECOVERY")
public class StudentRecoveryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_STUDENT_RECOVERY_IDT")
    @SequenceGenerator(name = "SQ_STUDENT_RECOVERY_IDT", sequenceName = "SQ_STUDENT_RECOVERY_IDT", allocationSize = 1)
    @Column(name = "IDT_STUDENT_RECOVERY", nullable = false, unique = true)
    private Long studentRecoveryId;

    @Column(name = "IDT_STUDENT", nullable = false)
    private Long studentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDT_SUBJECT", referencedColumnName = "IDT_SUBJECT", nullable = false)
    private SubjectEntity subject;

    public StudentRecoveryEntity() {
    }

    public StudentRecoveryEntity(Long studentId, SubjectEntity subject) {
        this.studentId = studentId;
        this.subject = subject;
    }

    public Long getStudentRecoveryId() {
        return studentRecoveryId;
    }

    public void setStudentRecoveryId(Long studentRecoveryId) {
        this.studentRecoveryId = studentRecoveryId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public SubjectEntity getSubject() {
        return subject;
    }

    public void setSubject(SubjectEntity subject) {
        this.subject = subject;
    }
}
