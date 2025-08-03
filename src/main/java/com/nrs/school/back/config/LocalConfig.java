package com.nrs.school.back.config;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.nrs.school.back.entities.ClassroomEntity;
import com.nrs.school.back.entities.StudentEntity;
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
        StudentEntity studentEntity1 = new StudentEntity(null, "Diego Stretz", "student1@fakeemail.com", null, "m423af1", new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        StudentEntity studentEntity2 = new StudentEntity(null, "Bruno Silva", "student2@fakeemail.com", null, "m34m1en", new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

        ClassroomEntity classroomEntity1 = new ClassroomEntity(null, "4°B", 4, "Brett Gaines", 1, 12, new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        ClassroomEntity classroomEntity2 = new ClassroomEntity(null, "3°C", 39, "Kieran Morrison", 2, 35, new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        ClassroomEntity classroomEntity3 = new ClassroomEntity(null, "7°D", 35, "Julio Mayo", 3, 48, new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        ClassroomEntity classroomEntity4 = new ClassroomEntity(null, "2°A", 40, "Hannah Doherty", 2, 97, new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        classroomRepository.saveAll(List.of(classroomEntity1, classroomEntity2, classroomEntity3, classroomEntity4));
        
        studentEntity1.setClassroomId(classroomRepository.findAll().get(0).getId());
        studentEntity2.setClassroomId(classroomRepository.findAll().get(0).getId());
        studentRepository.saveAll(List.of(studentEntity1, studentEntity2));

    }

    private void clearDB(){
        classroomRepository.deleteAll();
        studentRepository.deleteAll();
    }

}
