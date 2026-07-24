package com.nrs.school.back.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;

import com.nrs.school.back.StepDefinitionsDefault;
import com.nrs.school.back.fixtures.TestApiFixtures;
import com.nrs.school.back.repository.ClassroomRepository;
import com.nrs.school.back.repository.StudentRepository;
import com.nrs.school.back.repository.SubjectRepository;
import com.nrs.school.back.repository.UserRepository;

public class Hooks extends StepDefinitionsDefault {

    private final StudentRepository studentRepository;
    private final ClassroomRepository classroomRepository;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;

    public Hooks(StudentRepository studentRepository, ClassroomRepository classroomRepository, SubjectRepository subjectRepository, UserRepository userRepository) {
        this.studentRepository = studentRepository;
        this.classroomRepository = classroomRepository;
        this.subjectRepository = subjectRepository;
        this.userRepository = userRepository;
    }

    @Before
    @After
    public void cleanDatabase() {
        subjectRepository.deleteAll();
        studentRepository.deleteAll();
        classroomRepository.deleteAll();
        userRepository.deleteAll();
        TestApiFixtures.resetToken();
    }
}
