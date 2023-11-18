package com.nrs.school.back.mapper;

import com.nrs.school.back.entities.Students;
import com.nrs.school.back.entities.dto.StudentsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface StudentsMapperasdasd {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    StudentsDTO map(Students students);
}
