package com.nrs.school.back.steps;

import com.nrs.school.back.StepDefinitionsDefault;
import com.nrs.school.back.repository.ClassroomRepository;
import com.nrs.school.back.repository.StudentRepository;
import com.nrs.school.back.repository.UserRepository;
import io.cucumber.java.After;

public class Hooks extends StepDefinitionsDefault {

    private final StudentRepository studentRepository;
    private final ClassroomRepository classroomRepository;
    private final UserRepository userRepository;

    public Hooks(StudentRepository studentRepository, ClassroomRepository classroomRepository, UserRepository userRepository) {
        this.studentRepository = studentRepository;
        this.classroomRepository = classroomRepository;
        this.userRepository = userRepository;
    }

    @After
    public void cleanDatabase() {
        studentRepository.deleteAll();
        classroomRepository.deleteAll();
        userRepository.deleteAll();
    }
}
