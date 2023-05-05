package com.acme.hr.employee.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message){
        super(message);
    }
}
