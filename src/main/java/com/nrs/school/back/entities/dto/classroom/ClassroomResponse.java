package com.nrs.school.back.entities.dto.classroom;

import java.util.List;

public class ClassroomResponse {

    List<ClassroomDataResponse> classrooms;

    public ClassroomResponse(List<ClassroomDataResponse> classrooms) {
        this.classrooms = classrooms;
    }

    public ClassroomResponse (String teste) {
    }

    public ClassroomResponse() {
    }

    public List<ClassroomDataResponse> getClassrooms() {
        return classrooms;
    }

    public void setClassrooms(List<ClassroomDataResponse> classrooms) {
        this.classrooms = classrooms;
    }
}
