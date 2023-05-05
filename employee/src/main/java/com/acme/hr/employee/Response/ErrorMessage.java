package com.acme.hr.employee.Response;

import lombok.Data;

@Data
public class ErrorMessage {
	String message;
	String errorCode;
}