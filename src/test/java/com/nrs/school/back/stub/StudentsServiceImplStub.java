package com.nrs.school.back.stub;

import java.util.List;

import com.nrs.school.back.entities.Students;
import com.nrs.school.back.entities.dto.StudentsDTO;

public class StudentsServiceImplStub {

    public static List<StudentsDTO> getStudentsDTO(){
        StudentsDTO studentDto1 = new StudentsDTO(1, "Student 1", "m423af1");
        StudentsDTO studentDto2 = new StudentsDTO(2, "Student 2", "m34m1en");

        return List.of(studentDto1, studentDto2);
    }

    public static List<Students> getStudents(){
        Students student1 = new Students(1, "Student 1", "m423af1");
        Students student2 = new Students(2, "Student 2", "m34m1en");

        return List.of(student1, student2);
    }

    public static Students buildStudent(){
        return new Students(3, "Student 3", "c8a7s8e");
    }

    public static StudentsDTO buildStudentDTO(){
        return new StudentsDTO(3, "Student 3", "c8a7s8e");
    }

}
