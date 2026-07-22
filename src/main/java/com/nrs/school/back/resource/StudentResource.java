package com.nrs.school.back.resource;

import java.util.Arrays;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nrs.school.back.entities.dto.student.StudentDataRequest;
import com.nrs.school.back.entities.dto.student.StudentDataResponse;
import com.nrs.school.back.entities.dto.student.StudentResponse;
import com.nrs.school.back.service.StudentService;

@RestController
@RequestMapping(value = StudentResource.BASE_PATH)
public class StudentResource {

    @Value("${server.port}")
    private String port;

    public static final String BASE_PATH = "/students" ;

    private static final String REGISTRATION = "/{registration}";

    private static final String CLASSROOM_REFERENCE_CODE = "/classroom/{classroomId}";

    private final StudentService service;

    private final Environment env;

    public StudentResource(StudentService service, Environment env) {
        this.service = service;
        this.env = env;
    }

    @GetMapping
    public ResponseEntity<StudentResponse> findAll(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping(CLASSROOM_REFERENCE_CODE)
    public ResponseEntity<StudentResponse> findAllByClassroomReferenceCode(@PathVariable UUID classroomReferenceCode) {
        return ResponseEntity.ok().body(service.findByClassroomReferenceCode(classroomReferenceCode));
    }

    @GetMapping(REGISTRATION)
    public ResponseEntity<StudentDataResponse> findStudentByRegistration(@PathVariable String registration){
        return ResponseEntity.ok().body(service.findByRegistration(registration));
    }

    @PostMapping
    public ResponseEntity<StudentDataResponse> create(@RequestBody StudentDataRequest studentResponse){
        var servletUriComponentsBuilder = Arrays.stream(env.getActiveProfiles()).toList().contains("local") || Arrays.stream(env.getActiveProfiles()).toList().contains("test")
                ? ServletUriComponentsBuilder.fromCurrentRequest().port(port)
                : ServletUriComponentsBuilder.fromCurrentRequest();

        return ResponseEntity.created(servletUriComponentsBuilder.path(REGISTRATION).buildAndExpand(service.create(studentResponse).getRegistration()).toUri()).build();
    }

    @PutMapping
    public ResponseEntity<StudentDataResponse> update(@RequestBody StudentDataRequest studentResponse){
        var servletUriComponentsBuilder = Arrays.stream(env.getActiveProfiles()).toList().contains("local") || Arrays.stream(env.getActiveProfiles()).toList().contains("test")
                ? ServletUriComponentsBuilder.fromCurrentRequest().port(port)
                : ServletUriComponentsBuilder.fromCurrentRequest();

        var studentUpdated = service.update(studentResponse);

        return ResponseEntity.created(servletUriComponentsBuilder.path(REGISTRATION)
        .buildAndExpand(studentUpdated.getRegistration()).toUri()).body(studentUpdated);

    }

    @DeleteMapping(REGISTRATION)
    public ResponseEntity<String> delete(@PathVariable String registration){
        service.delete(registration);
        return ResponseEntity.noContent().build();

    }
}
