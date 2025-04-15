package com.nrs.school.back.resource;

import java.util.Arrays;
import java.util.List;

import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nrs.school.back.entities.dto.StudentDTO;
import com.nrs.school.back.service.StudentService;

@RestController
@RequestMapping(value = "/api/v1")
public class StudentResource {

    private static final String REGISTRATION = "/{registration}";

    private final StudentService service;

    private final Environment env;

    public StudentResource(StudentService service, Environment env) {
        this.service = service;
        this.env = env;
    }

    @GetMapping("/students")
    public ResponseEntity<List<StudentDTO>> findAll(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping("/student" + REGISTRATION)
    public ResponseEntity<StudentDTO> findStudentByRegistration(@PathVariable String registration){
        return ResponseEntity.ok().body(service.findByRegistration(registration));
    }

    @PostMapping("/create/student")
    public ResponseEntity<StudentDTO> create(@RequestBody StudentDTO studentDTO){
        var servletUriComponentsBuilder = Arrays.stream(env.getActiveProfiles()).toList().contains("local") || Arrays.stream(env.getActiveProfiles()).toList().contains("test")
                ? ServletUriComponentsBuilder.fromCurrentRequest().port("8080")
                : ServletUriComponentsBuilder.fromCurrentRequest();

        return ResponseEntity.created(servletUriComponentsBuilder.path("/api/v1/get/student/" + REGISTRATION).buildAndExpand(service.create(studentDTO).getStudentId()).toUri()).build();
    }

    @PutMapping("/update/student")
    public ResponseEntity<StudentDTO> update(@RequestBody StudentDTO studentDTO){
        var servletUriComponentsBuilder = Arrays.stream(env.getActiveProfiles()).toList().contains("local") || Arrays.stream(env.getActiveProfiles()).toList().contains("test")
                ? ServletUriComponentsBuilder.fromCurrentRequest().port("8080")
                : ServletUriComponentsBuilder.fromCurrentRequest();

        return ResponseEntity.created(servletUriComponentsBuilder.path("/api/v1/get/student/" + REGISTRATION)
        .buildAndExpand(service.update(studentDTO).getStudentId()).toUri()).build();
        
    }

    @DeleteMapping("/delete/student" + REGISTRATION)
    public ResponseEntity<StudentDTO> delete(@PathVariable String registration){
        service.delete(registration);
        return ResponseEntity.noContent().build();
        
    }
}
