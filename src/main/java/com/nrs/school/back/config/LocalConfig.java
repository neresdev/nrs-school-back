package com.nrs.school.back.config;

import com.nrs.school.back.entities.Students;
import com.nrs.school.back.repository.StudentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

    @Autowired
    private StudentsRepository repository;

    @Bean
    public void startDB(){
        Students students1 = new Students(1, "Student 1", "m423af1");
        Students students2 = new Students(2, "Student 2", "m34m1en");

        repository.saveAll(List.of(students1, students2));
    }
}
