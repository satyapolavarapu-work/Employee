package com.acme.hr.employee.exception;

public class IllegalDaysException extends RuntimeException {

    public IllegalDaysException(String message){
        super(message);
    }
}
