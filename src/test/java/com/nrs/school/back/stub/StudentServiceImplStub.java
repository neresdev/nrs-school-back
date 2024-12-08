package com.nrs.school.back.stub;

import java.util.List;

import com.nrs.school.back.entities.Student;
import com.nrs.school.back.entities.dto.StudentDTO;

public class StudentServiceImplStub {

    public static List<StudentDTO> getStudentsDTO(){
        //StudentDTO studentDto1 = new StudentDTO(1, "Student 1", "m423af1");
        //StudentDTO studentDto2 = new StudentDTO(2, "Student 2", "m34m1en");

        return null;
    }

    public static List<Student> getStudents(){
        //Student student1 = new Student(1, "Student 1", "m423af1");
        //Student student2 = new Student(2, "Student 2", "m34m1en");
        // TODO -> FIX
        return null;
    }

    public static Student buildStudent(){
        //return new Student(3, "Student 3", "c8a7s8e");
        return null;
    }

    public static StudentDTO buildStudentDTO(){
        //return new StudentDTO(3, "Student 3", "c8a7s8e");
        return null;
    }

    public static StudentDTO buildExistingStudentDTOWithThisRegistration(){
        //return new StudentDTO(2, "other student", "m34m1en");
        return null;
    }
    public static Student buildExistingStudentWithThisRegistration(){
        //return new Student(2, "other student", "m34m1en");
        return null;
    }

    public static StudentDTO buildInvalidStudentDTO(){
        return null;
        //var invalidName = new StringBuilder();
        //for(int i = 0; i <= 255; i++) invalidName.append("a");
        //return new StudentDTO(3, invalidName.toString(), "c8a7s8ea");
    }

}
