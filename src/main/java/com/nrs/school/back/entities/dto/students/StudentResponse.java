package com.nrs.school.back.entities.dto.students;

import java.util.Collections;
import java.util.List;

public class StudentResponse {

    private List<StudentDataResponse> students;

    public StudentResponse(List<StudentDataResponse> students) {
        this.students = students;
    }

    public StudentResponse(StudentDataResponse student) {
        this.students = Collections.singletonList(student);
    }

    public StudentResponse() {
    }

    public List<StudentDataResponse> getStudents() {
        return students;
    }
}
