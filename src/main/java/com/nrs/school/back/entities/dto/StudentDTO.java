package com.nrs.school.back.entities.dto;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class StudentDTO {

    @JsonIgnore
    private Integer id;
    
    @NotBlank(message = "Name cannot be blank or null")
    @Length(max = 255)
    private String name;

    @Length(min = 7, max = 7, message = "Registration must have 7 characters")
    private String registration;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }
}
