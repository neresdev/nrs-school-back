package com.nrs.school.back.config;

import com.nrs.school.back.entities.Student;
import com.nrs.school.back.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
@RequiredArgsConstructor
public class LocalConfig {
    
    private final StudentRepository repository;

    @Bean
    public void startDB(){
        Student student1 = new Student(1, "Student 1", "m423af1");
        Student student2 = new Student(2, "Student 2", "m34m1en");

        repository.saveAll(List.of(student1, student2));
    }
}
