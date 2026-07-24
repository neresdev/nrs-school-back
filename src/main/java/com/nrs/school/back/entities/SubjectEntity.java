package com.nrs.school.back.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "SUBJECT")
public class SubjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_SUBJECT_IDT")
    @SequenceGenerator(name = "SQ_SUBJECT_IDT", sequenceName = "SQ_SUBJECT_IDT", allocationSize = 1)
    @Column(name = "IDT_SUBJECT", nullable = false, unique = true)
    private Long subjectId;

    @Column(name = "SUBJECT_DESCRIPTION", nullable = false, length = 30)
    private String subjectDescription;

    public SubjectEntity() {
    }

    public SubjectEntity(String subjectDescription) {
        this.subjectDescription = subjectDescription;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectDescription() {
        return subjectDescription;
    }

    public void setSubjectDescription(String subjectDescription) {
        this.subjectDescription = subjectDescription;
    }
}
