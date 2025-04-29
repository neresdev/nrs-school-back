package com.nrs.school.back.enm;


public enum StudentError {
    STUDENT_CLASSROOM_NOT_FOUND("S001", "Student classroom does not exists");

    private String code;
    private String description;

    StudentError(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
