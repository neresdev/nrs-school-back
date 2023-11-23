package com.nrs.school.back.service.impl;

import com.nrs.school.back.entities.Students;
import com.nrs.school.back.entities.dto.StudentsDTO;
import com.nrs.school.back.exceptions.ObjectNotFoundException;
import com.nrs.school.back.repository.StudentsRepository;
import com.nrs.school.back.service.StudentsService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentsServiceImpl implements StudentsService {

    public static final String NOT_FOUND_MESSAGE = "Objeto nao encontrado";

    @Autowired
    private StudentsRepository repository;

    @Autowired
    private ModelMapper mapper;
    
    @Override
    public List<StudentsDTO> findAll() {
        return repository.findAll().stream().map(u -> mapper.map(u, StudentsDTO.class)).collect(Collectors.toList());
    }

    @Override
    public StudentsDTO findById(Integer id) {
        return mapper.map(repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(NOT_FOUND_MESSAGE)), StudentsDTO.class);
    }

    @Override
    public Students create(StudentsDTO studentsDTO) {
        return null;
    }

    @Override
    public Students update(StudentsDTO studentsDTO) {
        return null;
    }

    @Override
    public void delete(Integer studentId) {

    }
}
