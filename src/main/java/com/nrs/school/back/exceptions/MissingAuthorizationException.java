package com.nrs.school.back.exceptions;

public class MissingAuthorizationException extends RuntimeException{

    public MissingAuthorizationException(String message) {
        super(message);
    }
}
