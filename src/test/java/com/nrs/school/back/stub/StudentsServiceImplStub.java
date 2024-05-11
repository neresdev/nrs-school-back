package com.nrs.school.back.stub;

import com.nrs.school.back.entities.Students;
import com.nrs.school.back.entities.dto.StudentsDTO;

public class StudentsServiceImplStub {

    public static StudentsDTO getStudentsDTO(){
        StudentsDTO studentDTO = new StudentsDTO();
        
        studentDTO.setId(1);
        studentDTO.setName("Test");
        studentDTO.setRegistration("a8w9e8r");

        return studentDTO;
    }

    public static Students getStudents(){
        Students student = new Students();
        
        student.setId(1);
        student.setName("Test");
        student.setRegistration("a8w9e8r");

        return student;
    }

}
