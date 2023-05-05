package com.acme.hr.employee.entity;

import com.acme.hr.employee.exception.IllegalDaysException;
import com.acme.hr.employee.exception.InsufficientVacationBalanceException;

import java.util.ConcurrentModificationException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public abstract class Employee {

    protected final int YEAR_WORKDAYS = 260;

    final private Long employeeId;
    final private EmployeeType employeeType;

    /**
     * accumulate all work days
     */
    final private AtomicInteger totalDaysWorked;

    /**
     * accumulate all vacations
     */
    final private AtomicReference<Double> totalVacationsTaken;
    final private int applicableAnnualVacation;

    public Employee(final Long employeeId, final int applicableAnnualVacation){
        this.applicableAnnualVacation = applicableAnnualVacation;
        totalDaysWorked = new AtomicInteger(0);
        totalVacationsTaken = new AtomicReference(0.0);
        this.employeeId = employeeId;
        this.employeeType = employeeType();
    }


    private EmployeeType employeeType(){
        if(this instanceof HourlyEmployee){
            return EmployeeType.HOURLY;
        }else if(this instanceof SalariedEmployee){
            return EmployeeType.SALARIED;
        }if(this instanceof Manager){
            return EmployeeType.MANAGER;
        }
        //never happens
        return null;
    }
    /**
     * records work days & do validations
     * @param daysWorked
     */
    final public void work(int daysWorked){
        if(daysWorked<=0 || daysWorked > YEAR_WORKDAYS){
            final String message = String.format("Days worked should be between 0 and %s, provided value %s", YEAR_WORKDAYS, daysWorked);
            throw new IllegalDaysException(message);
        }
        totalDaysWorked.addAndGet(daysWorked);
    }

    /**
     * Records vacations & do validations
     * @param vacationsRequested
     */
    final public void takeVacation(double vacationsRequested) {
        Double vacationsTaken = totalVacationsTaken.get();
        double vacationsAccumulated = accumulatedVacation()- vacationsTaken;

        if(vacationsAccumulated < vacationsRequested){
            final String message = String.format("Vacations balance is %s which is less than %s", vacationsAccumulated, vacationsRequested);
            throw new InsufficientVacationBalanceException(message);
        }
        // Verifies if the instance level tracker is not updated from other thread, reverted if so
        // If not handled This may lead to a -ve vacationsAccumulated value
        if(!totalVacationsTaken.compareAndSet(vacationsTaken, vacationsRequested+vacationsTaken)){
            throw new ConcurrentModificationException("Vacations updated from multiple sources simultaneously");
        }
    }

    /**
     * Calculations based on Employee type, applicableAnnualVacation value depends on employee type
     * @return
     */
    private double accumulatedVacation() {
        return (totalDaysWorked.get() / YEAR_WORKDAYS) * applicableAnnualVacation;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public int getTotalDaysWorked() {
        return totalDaysWorked.get();
    }

    public double getTotalVacationsTaken() {
        return totalVacationsTaken.get();
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", employeeType=" + employeeType +
                ", totalDaysWorked=" + totalDaysWorked +
                ", totalVacationsTaken=" + totalVacationsTaken +
                '}';
    }
}
