package com.glam.userservice.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {
	@ExceptionHandler(Exception.class)
	ProblemDetail handleGenericException(Exception e) {

		HttpStatusCode httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
		String detail = e.getMessage();

		if (e instanceof ConstraintViolationException) {

			httpStatusCode = HttpStatus.BAD_REQUEST;

		}

		return ProblemDetail.forStatusAndDetail(httpStatusCode, detail);
	}
}
