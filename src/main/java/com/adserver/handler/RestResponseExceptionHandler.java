package com.adserver.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.adserver.exception.CampaignNotFoundException;
import com.adserver.model.ErrorResponse;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {
		if (ex instanceof CampaignNotFoundException) {
			return new ResponseEntity<Object>(new ErrorResponse(ex.getMessage()), new HttpHeaders(),
					HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Object>(new ErrorResponse(ex.getMessage()), new HttpHeaders(),
					HttpStatus.FORBIDDEN);
		}

	}
}