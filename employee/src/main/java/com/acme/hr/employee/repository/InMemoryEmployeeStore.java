package com.acme.hr.employee.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.acme.hr.employee.entity.Employee;
import com.acme.hr.employee.entity.EmployeeType;
import com.acme.hr.employee.exception.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InMemoryEmployeeStore implements EmployeeStore {

	private final Map<EmployeeType, Map<Long, Employee>> allEmployees = new HashMap<>();

	@Override
	public void saveAll(final EmployeeType employeeType, final List<Employee> employees) {
		allEmployees.put(employeeType,
				employees.stream().collect(Collectors.toMap(Employee::getEmployeeId, Function.identity())));
	}

	@Override
	public Employee get(final Long employeeId) {
		Optional<Employee> optionalEmployee = allEmployees.values().stream().flatMap(e -> e.values().stream())
				.filter(e1 -> e1.getEmployeeId().equals(employeeId)).findAny();
		if (!optionalEmployee.isPresent()) {
			final String message = String.format("Employee not found in employee store %s", employeeId);
			throw new NotFoundException(message);
		}
		return optionalEmployee.get();
	}

	@Override
	public void createOrReplace(Employee employee) {
		allEmployees.get(employee.getEmployeeType()).put(employee.getEmployeeId(), employee);
	}

	@Override
	public void delete(Employee employee) {
		Employee result = allEmployees.get(employee.getEmployeeType()).remove(employee.getEmployeeId());
		if (result == null) {
			log.warn("Employee not available {} ", result);
		} else {
			log.info("Deleted Employee {} ", result);
		}
	}
}
