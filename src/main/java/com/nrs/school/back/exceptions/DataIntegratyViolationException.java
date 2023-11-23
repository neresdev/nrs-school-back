package com.nrs.school.back.exceptions;

public class DataIntegratyViolationException extends RuntimeException {
    public DataIntegratyViolationException(String message){
        super(message);
    }
}
