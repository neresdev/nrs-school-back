package com.nrs.school.back.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nrs.school.back.entities.dto.subject.SubjectResponse;
import com.nrs.school.back.service.SubjectService;

@RestController
@RequestMapping(value = SubjectResource.BASE_PATH)
public class SubjectResource {

    public static final String BASE_PATH = "/subjects";

    private final SubjectService service;

    public SubjectResource(SubjectService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<SubjectResponse> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }
}
