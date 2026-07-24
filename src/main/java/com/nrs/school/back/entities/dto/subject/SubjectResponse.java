package com.nrs.school.back.entities.dto.subject;

import java.util.List;

public class SubjectResponse {

    private List<SubjectDataResponse> subjects;

    public SubjectResponse(List<SubjectDataResponse> subjects) {
        this.subjects = subjects;
    }

    public SubjectResponse() {
    }

    public List<SubjectDataResponse> getSubjects() {
        return subjects;
    }
}
