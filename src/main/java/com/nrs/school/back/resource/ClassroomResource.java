package com.nrs.school.back.resource;


import com.nrs.school.back.entities.dto.ClassroomDTO;
import com.nrs.school.back.entities.dto.StudentDTO;
import com.nrs.school.back.service.ClassroomService;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
public class ClassroomResource {

    private static final String CLASSROOM_ID = "/{classroomId}";

    private final ClassroomService classroomService;

    private final Environment env;

    public ClassroomResource(ClassroomService classroomService, Environment env) {
        this.classroomService = classroomService;
        this.env = env;
    }

    @GetMapping("/classrooms")
    public ResponseEntity<List<ClassroomDTO>> findAll() {
        return ResponseEntity.ok(classroomService.findAll());
    }

    @PostMapping("/create/classroom")
    public ResponseEntity<ClassroomDTO> create(@RequestBody ClassroomDTO classroomDTO){
        var servletUriComponentsBuilder = Arrays.stream(env.getActiveProfiles()).toList().contains("local") || Arrays.stream(env.getActiveProfiles()).toList().contains("test")
                ? ServletUriComponentsBuilder.fromCurrentRequest().port("8080")
                : ServletUriComponentsBuilder.fromCurrentRequest();

        return ResponseEntity
                .created(servletUriComponentsBuilder.path("/api/v1/get/classroom/" + CLASSROOM_ID)
                        .buildAndExpand(classroomService.create(classroomDTO)
                                .getClassroomName()
                                .replace("°", ""))
                        .toUri())
                .build();
    }
}
