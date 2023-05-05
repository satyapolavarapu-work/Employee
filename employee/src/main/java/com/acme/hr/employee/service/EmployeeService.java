package com.acme.hr.employee.service;

import com.acme.hr.employee.entity.*;
import com.acme.hr.employee.repository.EmployeeStore;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeStore employeeStore;

    @PostConstruct
    public void onInit(){
        List<Employee> hourlyEmployees = new ArrayList<>();
        List<Employee> salariedEmployees = new ArrayList<>();
        List<Employee> managers = new ArrayList<>();

        for(long initialId = 1; initialId <= 10 ; initialId++){
            hourlyEmployees.add(createEmployee(EmployeeType.HOURLY, initialId));
            salariedEmployees.add(createEmployee(EmployeeType.SALARIED, initialId+100));
            managers.add(createEmployee(EmployeeType.MANAGER, initialId+1000));
        }

        employeeStore.saveAll(EmployeeType.HOURLY, hourlyEmployees);
        employeeStore.saveAll(EmployeeType.SALARIED, salariedEmployees);
        employeeStore.saveAll(EmployeeType.MANAGER, managers);
    }

    public Employee createEmployee(final EmployeeType employeeType, Long employeeId){

        switch (employeeType){
            case HOURLY:
                return new HourlyEmployee(employeeId);
            case SALARIED:
                return new SalariedEmployee(employeeId);
            case MANAGER:
                return new Manager(employeeId);

        }
        throw new IllegalArgumentException("unexpected employee type");
    }

    public void addWork(Long employeeId, Integer daysWorked) {
        get(employeeId).work(daysWorked);
    }

    public void addVacation(Long employeeId, double vacationRequested) {
        get(employeeId).takeVacation(vacationRequested);
    }

    public Employee get(Long employeeId) {
        return employeeStore.get(employeeId);
    }
}
