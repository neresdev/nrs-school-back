package com.nrs.school.back.exceptions;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;

@Getter
public class StandardError{

    @JsonIgnore
    private LocalDateTime timestamp;
    
    private Integer status;
    
    private String error;
    
    @JsonIgnore
    private String path;

    public StandardError(LocalDateTime timestamp, Integer status, String error, String path){
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.path = path;
    }
    

}
