package com.nrs.school.back.controller;

import com.nrs.school.back.entities.dto.StudentsDTO;
import com.nrs.school.back.service.StudentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class StudentController {

    private static final String ID = "/{id}";

    @Autowired
    private StudentsService service;

    @GetMapping("/all/students")
    public ResponseEntity<List<StudentsDTO>> findAll(){
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping("/student" + ID)
    public ResponseEntity<StudentsDTO> findStudentById(@PathVariable Integer id){
        return ResponseEntity.ok().body(service.findById(id));
    }
}
