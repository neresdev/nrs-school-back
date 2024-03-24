package com.nrs.school.back.controller;

import com.nrs.school.back.entities.dto.StudentsDTO;
import com.nrs.school.back.service.StudentsService;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.List;

@RestController
@RequestMapping(value = "/api") // add API version
public class StudentController {

    private static final String REGISTRATION = "/{registration}";

    @Autowired
    private StudentsService service; // add @RequiredArgsConstructor

    @GetMapping("/all/students")
    public ResponseEntity<List<StudentsDTO>> findAll(){
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping("/get/student" + REGISTRATION)
    public ResponseEntity<StudentsDTO> findStudentById(@PathVariable String registration){
        return ResponseEntity.ok().body(service.findByRegistration(registration));
    }

    @PostMapping("/create/student")
    public ResponseEntity<StudentsDTO> create(@RequestBody StudentsDTO studentsDTO){
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path(REGISTRATION)
        .buildAndExpand(service.create(studentsDTO).getId()).toUri()).build(); 
    }

    @PutMapping("/update/student")
    public ResponseEntity<StudentsDTO> update(@RequestBody StudentsDTO studentsDTO){
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path(REGISTRATION)
        .buildAndExpand(service.update(studentsDTO).getId()).toUri()).build();
        
    }

    @DeleteMapping("/delete/student" + REGISTRATION)
    public ResponseEntity<StudentsDTO> delete(@PathVariable String registration){
        service.delete(registration);
        return ResponseEntity.noContent().build();
        
    }
}
