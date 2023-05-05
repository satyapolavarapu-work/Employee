package com.acme.hr.employee.entity;

public class SalariedEmployee extends Employee{

    public SalariedEmployee(final Long employeeId){
        super(employeeId,15);
    }

    public SalariedEmployee(final Long employeeId, int applicableVacations){
        super(employeeId, applicableVacations);
    }
}

