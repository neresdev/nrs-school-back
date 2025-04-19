package com.nrs.school.back.entities.dto;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;

public class StudentDTO {

    @JsonIgnore
    private Long studentId;

    @NotBlank(message = "Name cannot be blank or null")
    @Length(max = 255)
    private String studentName;

    @NotBlank(message = "Email cannot be blank or null")
    private String studentEmail;

    @Length(max = 3, min = 3, message = "Classroom must have 3 characters")
    private String classroomName;

    @Length(min = 7, max = 7, message = "Registration must have 7 characters")
    private String registration;

    public StudentDTO() {
    }

    public StudentDTO(Long studentId, String studentName, String studentEmail, String classroomName, String registration) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.registration = registration;
        this.classroomName = classroomName;
    }

    public StudentDTO(@NotBlank(message = "Name cannot be blank or null") @Length(max = 255) String studentName,
                      @NotBlank(message = "Email cannot be blank or null") String studentEmail, String classroomName,
                      @Length(min = 7, max = 7, message = "Registration must have 7 characters") String registration) {
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.classroomName = classroomName;
        this.registration = registration;
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

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    
}
