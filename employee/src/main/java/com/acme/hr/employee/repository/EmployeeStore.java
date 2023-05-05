package com.acme.hr.employee.repository;

import com.acme.hr.employee.entity.Employee;
import com.acme.hr.employee.entity.EmployeeType;

import java.util.List;

public interface EmployeeStore {

    void saveAll(final EmployeeType employeeType, final List<Employee> employees);

    Employee get(final Long employeeId);

    void createOrReplace(Employee employee);

    void delete(Employee employee);
}
