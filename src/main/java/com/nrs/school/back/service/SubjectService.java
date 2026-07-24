package com.nrs.school.back.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.nrs.school.back.entities.dto.subject.SubjectDataResponse;
import com.nrs.school.back.entities.dto.subject.SubjectResponse;
import com.nrs.school.back.repository.SubjectRepository;

@Component
public class SubjectService {

    private final SubjectRepository repository;
    private final ModelMapper mapper;

    public SubjectService(SubjectRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public SubjectResponse findAll() {
        final var entities = repository.findAll();
        final var response = entities.stream()
                .map(entity -> mapper.map(entity, SubjectDataResponse.class))
                .toList();
        return new SubjectResponse(response);
    }
}
