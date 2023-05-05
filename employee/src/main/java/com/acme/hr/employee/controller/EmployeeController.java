package com.acme.hr.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acme.hr.employee.Response.EmployeeSuccessResponse;
import com.acme.hr.employee.entity.Employee;
import com.acme.hr.employee.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@PutMapping("/{employeeId}/work/{daysWorked}")
	public ResponseEntity<EmployeeSuccessResponse> work(@PathVariable("employeeId") Long employeeId,
			@PathVariable("daysWorked") Integer daysWorked) {
		employeeService.addWork(employeeId, daysWorked);
		return ResponseEntity.ok(EmployeeSuccessResponse.builder().employee(employeeService.get(employeeId))
				.message("Work days updated").build());
	}

	@GetMapping("/{employeeId}")
	public ResponseEntity<Employee> getAmployee(@PathVariable("employeeId") Long employeeId) {
		return ResponseEntity.ok(employeeService.get(employeeId));
	}

	@PutMapping("/{employeeId}/vacation/{vacationRequested}")
	public ResponseEntity<EmployeeSuccessResponse> vacation(@PathVariable("employeeId") Long employeeId,
			@PathVariable("vacationRequested") Double vacationRequested) {
		employeeService.addVacation(employeeId, vacationRequested);
		return ResponseEntity.ok(EmployeeSuccessResponse.builder().employee(employeeService.get(employeeId))
				.message("vacations updated").build());
	}

}
