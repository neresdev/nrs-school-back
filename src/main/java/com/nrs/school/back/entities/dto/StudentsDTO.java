package com.nrs.school.back.entities.dto;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentsDTO {

    @JsonIgnore
    private Integer id;
    
    @NotBlank(message = "Name cannot be blank or null")
    @Length(max = 255)
    private String name;

    @Length(min = 7, max = 7, message = "Registration must have 7 characters")
    private String registration;
}
