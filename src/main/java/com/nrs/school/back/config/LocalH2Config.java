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
@Profile({"local-h2", "local-mysql", "dev"})
public class LocalH2Config {
    private static final Logger log = Logger.getLogger(LocalH2Config.class.getName());

    private final StudentRepository studentRepository;
    private final ClassroomRepository classroomRepository;

    public LocalH2Config(StudentRepository studentRepository, ClassroomRepository classroomRepository) {
        this.studentRepository = studentRepository;
        this.classroomRepository = classroomRepository;
    }

    @Bean
    public void startDB(){
        clearDB();
        log.info("Populando banco...");
        Student student1 = new Student(null, "Student 1", "student1@gmail.com", null, "m423af1", new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        Student student2 = new Student(null, "Student 2", "student2@gmail.com", null, "m34m1en", new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

        Classroom classroom = new Classroom(null, "4°B", 4, "Teacher 1", 1, 12, new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(), null);
        classroomRepository.save(classroom);
        
        student1.setClassroom(classroom);
        student2.setClassroom(classroom);
        studentRepository.saveAll(List.of(student1, student2));
        log.info("Banco populado com sucesso");

    }

    private void clearDB(){
        classroomRepository.deleteAll();
        studentRepository.deleteAll();
    }

}
