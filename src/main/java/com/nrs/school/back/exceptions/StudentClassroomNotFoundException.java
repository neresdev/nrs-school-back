package com.nrs.school.back.exceptions;

public class StudentClassroomNotFoundException extends RuntimeException {
    public StudentClassroomNotFoundException(String message){
        super(message);
    }
}
