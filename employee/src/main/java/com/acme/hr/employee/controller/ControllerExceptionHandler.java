package com.acme.hr.employee.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.acme.hr.employee.Response.ErrorMessage;
import com.acme.hr.employee.exception.IllegalDaysException;
import com.acme.hr.employee.exception.InsufficientVacationBalanceException;
import com.acme.hr.employee.exception.NotFoundException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;

@RestControllerAdvice
@ResponseBody
public class ControllerExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ErrorMessage resourceNotFoundException(NotFoundException ex, HttpServletRequest request) {
		ErrorMessage message = new ErrorMessage();
		message.setMessage(ex.getMessage());
		message.setErrorCode("client_001");
		return message;
	}

	@ExceptionHandler(InsufficientVacationBalanceException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorMessage insufficientVacationBalanceException(InsufficientVacationBalanceException ex,
			HttpServletRequest request) {
		ErrorMessage message = new ErrorMessage();
		message.setMessage(ex.getMessage());
		message.setErrorCode("client_002");
		return message;
	}

	@ExceptionHandler(IllegalDaysException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorMessage illegalDaysExceptionException(IllegalDaysException ex, HttpServletRequest request) {
		ErrorMessage message = new ErrorMessage();
		message.setMessage(ex.getMessage());
		message.setErrorCode("client_002");
		return message;
	}

}