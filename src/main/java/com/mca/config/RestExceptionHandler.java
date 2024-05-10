package com.mca.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mca.bean.ServiceResponseError;
import com.mca.exception.BadRequestException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	/***
	 * Handle BadRequestException
	 * 
	 * @param badRequestException
	 * @return ResponseEntity<Object>
	 */
	protected ResponseEntity<Object> handleBadRequestException(BadRequestException badRequestException) {

		ServiceResponseError serviceResponseError = new ServiceResponseError(HttpStatus.BAD_REQUEST);
		serviceResponseError.setMessage(badRequestException.getMessage());
		serviceResponseError.setDebugMessage(badRequestException.getMessage());
		return buildResponseEntity(serviceResponseError);

	}

	/***
	 * Build the response entity
	 * 
	 * @param serviceResponseError
	 * @return ResponseEntity
	 */
	private ResponseEntity<Object> buildResponseEntity(ServiceResponseError serviceResponseError) {
		return new ResponseEntity<>(serviceResponseError, serviceResponseError.getStatus());
	}
}
