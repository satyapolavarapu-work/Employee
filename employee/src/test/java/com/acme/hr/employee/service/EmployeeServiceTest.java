package com.acme.hr.employee.service;

import com.acme.hr.employee.entity.Employee;
import com.acme.hr.employee.exception.IllegalDaysException;
import com.acme.hr.employee.exception.InsufficientVacationBalanceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeServiceTest {

    @Autowired
    EmployeeService employeeService;

    @Test
    void testEmployeeStoreDataPopulation() {
        Employee hourlyEmployee = employeeService.get(5l);
        assertNotNull(hourlyEmployee);

        Employee salariedEmployee = employeeService.get(105l);
        assertNotNull(salariedEmployee);

        Employee manager = employeeService.get(1005l);
        assertNotNull(manager);
    }

    @Test
    void testHourlyInvalidWork(){
        Employee hourlyEmployee = employeeService.get(5l);
        assertNotNull(hourlyEmployee);
        IllegalDaysException thrown = assertThrows(
                IllegalDaysException.class,
                () -> employeeService.addWork(hourlyEmployee.getEmployeeId(), 261),
                "Expected IllegalDaysException, but it didn't"
        );
        assertTrue(thrown.getMessage().contentEquals("Days worked should be between 0 and 260, provided value 261"));


        InsufficientVacationBalanceException thrownVacations = assertThrows(
                InsufficientVacationBalanceException.class,
                () -> employeeService.addVacation(hourlyEmployee.getEmployeeId(), 25),
                "Expected InsufficientVacationBalanceException, but it didn't"
        );
        assertTrue(thrownVacations.getMessage().contentEquals("Vacations balance is 0.0 which is less than 25.0"));
    }

    @Test
    void testHourly(){
        Employee hourlyEmployee = employeeService.get(5l);
        assertNotNull(hourlyEmployee);
        employeeService.addWork(hourlyEmployee.getEmployeeId(), 260);
        employeeService.addVacation(hourlyEmployee.getEmployeeId(), 10);
        assertWorkAndVacations(hourlyEmployee.getEmployeeId(), 260, 10.0);

        //Should throw error, requesting additional leave
        InsufficientVacationBalanceException thrownVacations = assertThrows(
                InsufficientVacationBalanceException.class,
                () -> employeeService.addVacation(hourlyEmployee.getEmployeeId(), 1),
                "Expected InsufficientVacationBalanceException, but it didn't"
        );
        assertTrue(thrownVacations.getMessage().contentEquals("Vacations balance is 0.0 which is less than 1.0"));

        //adding 1 less day than required
        //Should throw error, requesting additional leave
        employeeService.addWork(hourlyEmployee.getEmployeeId(), 259);
        InsufficientVacationBalanceException thrownVacations1 = assertThrows(
                InsufficientVacationBalanceException.class,
                () -> employeeService.addVacation(hourlyEmployee.getEmployeeId(), 1),
                "Expected InsufficientVacationBalanceException, but it didn't"
        );
        assertTrue(thrownVacations1.getMessage().contentEquals("Vacations balance is 0.0 which is less than 1.0"));


        //adding 1 more work day
        employeeService.addWork(hourlyEmployee.getEmployeeId(), 1);
        //Vacation granted without error
        employeeService.addVacation(hourlyEmployee.getEmployeeId(), 10);
        assertWorkAndVacations(hourlyEmployee.getEmployeeId(), 520, 20.0);

    }


    private void assertWorkAndVacations(Long id, int work, double vacations){
        Employee employee = employeeService.get(id);
        assertNotNull(employee);
        assertEquals(work, employee.getTotalDaysWorked());
        assertEquals(vacations, employee.getTotalVacationsTaken());
    }
}