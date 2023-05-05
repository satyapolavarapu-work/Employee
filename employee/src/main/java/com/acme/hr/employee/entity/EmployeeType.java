package com.acme.hr.employee.entity;

import java.util.HashMap;
import java.util.Map;

public enum EmployeeType {

    HOURLY("1"), SALARIED("2"), MANAGER("3");

    private static final Map<String, EmployeeType> BY_EMPLOYEE_TYPE = new HashMap<>();
    private static final Map<String, String> BY_CLASS = new HashMap<>();

    static {
        for (EmployeeType e: values()) {
            BY_EMPLOYEE_TYPE.put(e.type, e);
        }
    }

    public static EmployeeType byType(String type) {
        return BY_EMPLOYEE_TYPE.get(type);
    }

    String type;
    EmployeeType(String type){
        this.type = type;
    }

}
