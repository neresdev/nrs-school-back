package com.nrs.school.back.service;

import com.nrs.school.back.entities.dto.ClassroomDTO;
import com.nrs.school.back.repository.ClassroomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ClassroomService {

    private final ClassroomRepository repository;

    private final ModelMapper mapper;

    public ClassroomService(ClassroomRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ClassroomDTO> findAll() {
        return repository.findAll().stream().map(c -> {
            final var classroomDTO = mapper.map(c, ClassroomDTO.class);
            classroomDTO.setRequestId(UUID.randomUUID());
            return classroomDTO;
        }).toList();
    }
}
