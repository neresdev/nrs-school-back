package com.nrs.school.back.config;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.nrs.school.back.entities.Classroom;
import com.nrs.school.back.entities.Student;
import com.nrs.school.back.repository.ClassroomRepository;
import com.nrs.school.back.repository.StudentRepository;

@Configuration
@Profile({"local-h2", "local-mysql", "test"})
public class LocalConfig {
    private static final Logger log = Logger.getLogger(LocalConfig.class.getName());

    private final StudentRepository studentRepository;
    private final ClassroomRepository classroomRepository;

    public LocalConfig(StudentRepository studentRepository, ClassroomRepository classroomRepository) {
        this.studentRepository = studentRepository;
        this.classroomRepository = classroomRepository;
    }

    @Bean
    public void startDB(){
        clearDB();
        Student student1 = new Student(null, "Student 1", "student1@fakeemail.com", null, "m423af1", new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        Student student2 = new Student(null, "Student 2", "student2@fakeemail.com", null, "m34m1en", new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

        Classroom classroom1 = new Classroom(null, "4°B", 4, "Teacher 1", 1, 12, new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        Classroom classroom2 = new Classroom(null, "3°C", 39, "Teacher 2", 2, 35, new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        Classroom classroom3 = new Classroom(null, "7°D", 35, "Teacher 3", 3, 48, new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        Classroom classroom4 = new Classroom(null, "2°A", 40, "Teacher 4", 2, 97, new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        classroomRepository.saveAll(List.of(classroom1, classroom2, classroom3, classroom4));
        
        student1.setClassroomId(classroomRepository.findAll().get(0).getId());
        student2.setClassroomId(classroomRepository.findAll().get(0).getId());
        studentRepository.saveAll(List.of(student1, student2));

    }

    private void clearDB(){
        classroomRepository.deleteAll();
        studentRepository.deleteAll();
    }

}
