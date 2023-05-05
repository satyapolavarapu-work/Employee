package com.acme.hr.employee.Response;

import com.acme.hr.employee.entity.Employee;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeSuccessResponse {
	Employee employee;
	String message;
}