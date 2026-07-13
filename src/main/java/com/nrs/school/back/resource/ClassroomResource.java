package com.nrs.school.back.resource;


import com.nrs.school.back.entities.dto.classroom.ClassroomDataResponse;
import com.nrs.school.back.entities.dto.classroom.ClassroomResponse;
import com.nrs.school.back.entities.dto.student.StudentResponse;
import com.nrs.school.back.service.ClassroomService;
import com.nrs.school.back.service.StudentService;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ClassroomResource.BASE_PATH)
public class ClassroomResource {

    public static final String BASE_PATH = "/classrooms";

    private static final String CLASSROOM_ID = "/{classroomId}";

    private final ClassroomService classroomService;
    private final StudentService studentService;

    private final Environment env;

    public ClassroomResource(ClassroomService classroomService, StudentService studentService, Environment env) {
        this.classroomService = classroomService;
        this.studentService = studentService;
        this.env = env;
    }

    @GetMapping
    public ResponseEntity<ClassroomResponse> findAll() {
        return ResponseEntity.ok(classroomService.findAll());
    }

    @GetMapping(CLASSROOM_ID)
    public ResponseEntity<ClassroomDataResponse> findByClassroomReferenceCode(@PathVariable UUID classroomReferenceCode) {
        return ResponseEntity.ok(classroomService.findByClassroomReferenceCode(classroomReferenceCode));
    }

    @GetMapping("/classroom-students/{classroomId}")
    public ResponseEntity<StudentResponse> findStudentsByClassroomId(@PathVariable UUID classroomId) {
        return ResponseEntity.ok(studentService.findByClassroomReferenceCode(classroomId));
    }

    @PostMapping("/create")
    public ResponseEntity<ClassroomDataResponse> create(@RequestBody ClassroomDataResponse classroomDataResponse){
        var servletUriComponentsBuilder = Arrays.stream(env.getActiveProfiles()).toList().contains("local") || Arrays.stream(env.getActiveProfiles()).toList().contains("test")
                ? ServletUriComponentsBuilder.fromCurrentRequest().port("8080")
                : ServletUriComponentsBuilder.fromCurrentRequest();

        return ResponseEntity
                .created(servletUriComponentsBuilder.path("/api/v1/get/classroom/" + CLASSROOM_ID)
                    .buildAndExpand(classroomService.create(classroomDataResponse)
                        .getClassroomName()
                        .replace("°", ""))
                    .toUri())
                .build();
    }
}
