package com.nrs.school.back.controller;

import com.nrs.school.back.entities.Students;
import com.nrs.school.back.entities.dto.StudentsDTO;
import com.nrs.school.back.service.StudentsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class StudentController {

    private static final String ID = "/{id}";

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private StudentsService service;

    @GetMapping("/all/students")
    public ResponseEntity<List<StudentsDTO>> findAll(){
        return ResponseEntity.ok()
                .body(service.findAll().stream().map(u -> mapper.map(u, StudentsDTO.class)).collect(Collectors.toList()));
    }
}
