package com.nrs.school.back.exceptions;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class StandardError{
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String path;

    public StandardError(LocalDateTime timestamp, Integer status, String error, String path){
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.path = path;
    }

}
