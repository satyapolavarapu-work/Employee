package com.acme.hr.employee.exception;

public class InsufficientVacationBalanceException extends RuntimeException {

    public InsufficientVacationBalanceException(String message){
        super(message);
    }
}
