package com.nrs.school.back.exceptions;

public class DataIntegrityViolationException extends RuntimeException {
    public DataIntegrityViolationException(String message){
        super(message);
    }
}
