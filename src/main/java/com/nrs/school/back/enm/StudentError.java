package com.nrs.school.back.enm;


public enum StudentError {
    STUDENT_CLASSROOM_NOT_FOUND("S001");

    private String code;

    StudentError(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
