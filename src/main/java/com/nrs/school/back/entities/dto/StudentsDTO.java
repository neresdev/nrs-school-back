package com.nrs.school.back.entities.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentsDTO {
    @JsonIgnore
    private Integer id;
    private String name;
}
