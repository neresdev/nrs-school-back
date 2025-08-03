package com.nrs.school.back.entities;

import jakarta.persistence.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash(value = "CLASSROOMS", timeToLive = 10)
public class RedisClassroomEntity implements Serializable {

    @Id
    private Long id;


    private String classroomName;

    private int capacity;

    private String teacher;

    private int shift;

    private int classNumber;

    private String classroomReferenceCode;

    public RedisClassroomEntity() {
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    public int getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(int classNumber) {
        this.classNumber = classNumber;
    }

    public String getClassroomReferenceCode() {
        return classroomReferenceCode;
    }

    public void setClassroomReferenceCode(String classroomReferenceCode) {
        this.classroomReferenceCode = classroomReferenceCode;
    }
}
