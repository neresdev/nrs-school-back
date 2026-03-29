package com.nrs.school.back.entities.dto.student;

import java.util.List;

public class StudentResponse {

    private List<StudentDataResponse> students;

    public StudentResponse(List<StudentDataResponse> students) {
        this.students = students;
    }

    public StudentResponse() {
    }

    public List<StudentDataResponse> getStudents() {
        return students;
    }
}
