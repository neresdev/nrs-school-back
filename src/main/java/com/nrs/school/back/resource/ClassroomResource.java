package com.nrs.school.back.resource;


import com.nrs.school.back.entities.dto.ClassroomDTO;
import com.nrs.school.back.service.ClassroomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
public class ClassroomResource {

    private final ClassroomService classroomService;

    public ClassroomResource(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @GetMapping("/classrooms")
    public ResponseEntity<List<ClassroomDTO>> findAll() {
        return ResponseEntity.ok(classroomService.findAll());
    }
}
