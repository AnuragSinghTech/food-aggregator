package com.assignment.foodAggregator.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomizedResponseExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionResponseFormat> handleAllException(Exception ex, WebRequest request)
			throws Exception {
		ExceptionResponseFormat exFormat = new ExceptionResponseFormat(new Date(), ex.getMessage());
		return new ResponseEntity<ExceptionResponseFormat>(exFormat, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(CustomException.class)
	public final ResponseEntity<ExceptionResponseFormat> handleCustomException(CustomException ex, WebRequest request)
			throws Exception {
		ExceptionResponseFormat exFormat = new ExceptionResponseFormat(new Date(), ex.getMessage());
		return new ResponseEntity<ExceptionResponseFormat>(exFormat, HttpStatus.NOT_FOUND);
	}

}
