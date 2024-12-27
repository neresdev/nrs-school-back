package com.nrs.school.back.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nrs.school.back.entities.dto.StudentDTO;
import com.nrs.school.back.service.StudentService;

@RestController
@RequestMapping(value = "/api/v1")
public class StudentController {

    private static final String REGISTRATION = "/{registration}";

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping("/all/students")
    public ResponseEntity<List<StudentDTO>> findAll(){
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping("/get/student" + REGISTRATION)
    public ResponseEntity<StudentDTO> findStudentByRegistration(@PathVariable String registration){
        return ResponseEntity.ok().body(service.findByRegistration(registration));
    }

    @PostMapping("/create/student")
    public ResponseEntity<StudentDTO> create(@RequestBody StudentDTO studentDTO){
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path(REGISTRATION)
        .buildAndExpand(service.create(studentDTO).getStudentId()).toUri()).build();
    }

    @PutMapping("/update/student")
    public ResponseEntity<StudentDTO> update(@RequestBody StudentDTO studentDTO){
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path(REGISTRATION)
        .buildAndExpand(service.update(studentDTO).getStudentId()).toUri()).build();
        
    }

    @DeleteMapping("/delete/student" + REGISTRATION)
    public ResponseEntity<StudentDTO> delete(@PathVariable String registration){
        service.delete(registration);
        return ResponseEntity.noContent().build();
        
    }
}
