package com.nrs.school.back.stub;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import com.nrs.school.back.entities.Student;
import com.nrs.school.back.entities.dto.StudentDTO;

public class StudentServiceImplStub {

    public static List<StudentDTO> getStudentsDTO(){
        StudentDTO studentDto1 = new StudentDTO(1l, "Student 1", "student1@fakeemail.com", 2l, "m423af1");
        StudentDTO studentDto2 = new StudentDTO(2l, "Student 2", "student2@fakeemail.com", 2l, "m34m1en");

        return List.of(studentDto1, studentDto2);
    }

    public static List<Student> getStudents(){
        Student student1 = new Student(1l, "Student 1", "student1@fakeemail.com", 2l, "m423af1", new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        Student student2 = new Student(2l, "Student 2", "student2@fakeemail.com", 2l, "m34m1en", new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        
        return List.of(student1, student2);
    }

    public static Student buildStudent(){
        return new Student(3l, "Student 3", "student3@fakeemail.com", null, "c8a7s8e", new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
    }

    public static StudentDTO buildStudentDTO(){
        return new StudentDTO(3l, "Student 3", "student3@fakeemail.com", 2l, "c8a7s8e");
    }

    public static StudentDTO buildExistingStudentDTOWithThisRegistration(){
        return new StudentDTO(2l, "other student", "otherstudent@fakeemail.com", 2l, "m34m1en");
    }
    public static Student buildExistingStudentWithThisRegistration(){
        return new Student(2l, "other student", "otherstudent@fakeemail.com", 2l, "m34m1en", new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
    }

    public static StudentDTO buildInvalidStudentDTO(){
        var invalidName = new StringBuilder();
        for(int i = 0; i <= 255; i++) invalidName.append("a");
        return new StudentDTO(3l, invalidName.toString(), "student@fakeemail.com", 2l, "c8a7s8ea");
    }

}
