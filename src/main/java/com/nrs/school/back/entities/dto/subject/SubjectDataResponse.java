package com.nrs.school.back.entities.dto.subject;

public class SubjectDataResponse {

    private String subjectDescription;

    public SubjectDataResponse() {
    }

    public SubjectDataResponse(String subjectDescription) {
        this.subjectDescription = subjectDescription;
    }

    public String getSubjectDescription() {
        return subjectDescription;
    }

    public void setSubjectDescription(String subjectDescription) {
        this.subjectDescription = subjectDescription;
    }
}
