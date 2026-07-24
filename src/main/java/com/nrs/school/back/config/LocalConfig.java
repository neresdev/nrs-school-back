package com.nrs.school.back.config;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nrs.school.back.entities.ClassroomEntity;
import com.nrs.school.back.entities.StudentEntity;
import com.nrs.school.back.entities.SubjectEntity;
import com.nrs.school.back.entities.UserEntity;
import com.nrs.school.back.repository.ClassroomRepository;
import com.nrs.school.back.repository.StudentRepository;
import com.nrs.school.back.repository.SubjectRepository;
import com.nrs.school.back.repository.UserRepository;

@Configuration
@Profile({"local-h2", "local-postgres", "test"})
public class LocalConfig {
    private final StudentRepository studentRepository;
    private final ClassroomRepository classroomRepository;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LocalConfig(StudentRepository studentRepository, ClassroomRepository classroomRepository, SubjectRepository subjectRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.classroomRepository = classroomRepository;
        this.subjectRepository = subjectRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public void startDB(){
        clearDB();

        SubjectEntity subjectEntity1 = new SubjectEntity("Matemática");
        SubjectEntity subjectEntity2 = new SubjectEntity("Português");
        SubjectEntity subjectEntity3 = new SubjectEntity("Ciências");
        SubjectEntity subjectEntity4 = new SubjectEntity("História");
        subjectRepository.saveAll(List.of(subjectEntity1, subjectEntity2, subjectEntity3, subjectEntity4));

        UserEntity userEntity = new UserEntity();
        userEntity.setFullName("Admin");
        userEntity.setEmail("admin@admin.com");
        userEntity.setPassword(passwordEncoder.encode("admin"));
        userRepository.save(userEntity);

        StudentEntity studentEntity1 = new StudentEntity("Diego Stretz", "student1@fakeemail.com", "m423af1");
        StudentEntity studentEntity2 = new StudentEntity("Bruno Silva", "student2@fakeemail.com", "m34m1en");

        ClassroomEntity classroomEntity1 = new ClassroomEntity(null, "4°B", 4, "Brett Gaines", 1, 12, new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(), UUID.randomUUID());
        ClassroomEntity classroomEntity2 = new ClassroomEntity(null, "3°C", 39, "Kieran Morrison", 2, 35, new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(), UUID.randomUUID());
        ClassroomEntity classroomEntity3 = new ClassroomEntity(null, "7°D", 35, "Julio Mayo", 3, 48, new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(), UUID.randomUUID());
        ClassroomEntity classroomEntity4 = new ClassroomEntity(null, "2°A", 40, "Hannah Doherty", 2, 97, new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(), UUID.randomUUID());
        classroomRepository.saveAll(List.of(classroomEntity1, classroomEntity2, classroomEntity3, classroomEntity4));

        studentEntity1.setClassroomId(classroomRepository.findAll().get(0).getId());
        studentEntity2.setClassroomId(classroomRepository.findAll().get(0).getId());
        studentRepository.saveAll(List.of(studentEntity1, studentEntity2));

    }

    private void clearDB(){
        userRepository.deleteAll();
        classroomRepository.deleteAll();
        studentRepository.deleteAll();
        subjectRepository.deleteAll();
    }

}
