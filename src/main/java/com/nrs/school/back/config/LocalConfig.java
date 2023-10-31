package com.nrs.school.back.config;

import com.nrs.school.back.entities.Student;
import com.nrs.school.back.repository.StudentRepostitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

    @Autowired
    private StudentRepostitory repository;

    @Bean
    public void startDB(){
        Student student1 = new Student(1, "Student 1");
        Student student2 = new Student(2, "Student 2");

        repository.saveAll(List.of(student1, student2));
    }
}
