package com.nrs.school.back.entities.dto;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;

public class StudentDTO {

    @JsonIgnore
    private Integer studentId;

    @NotBlank(message = "Name cannot be blank or null")
    @Length(max = 255)
    private String studentName;

    @NotBlank(message = "Email cannot be blank or null")
    private String studentEmail;

    private Long classRoomId;

    @Length(min = 7, max = 7, message = "Registration must have 7 characters")
    private String registration;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
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

    public Long getClassRoomId() {
        return classRoomId;
    }

    public void setClassRoomId(Long classRoomId) {
        this.classRoomId = classRoomId;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    
}
