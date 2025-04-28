package com.nrs.school.back.exceptions;

import com.nrs.school.back.enm.StudentError;

public class StudentClassroomNotFoundException extends RuntimeException {
    private String code;

    public StudentClassroomNotFoundException(String message, StudentError studentError){
        super(message);
        this.code = studentError.getCode();
    }

    public String getCode() {
        return code;
    }
}
