package onlinecafeteria.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import onlinecafeteria.controller.exception.ErrorResponse;
import onlinecafeteria.controller.exception.ProductNotFoundException;
import onlinecafeteria.controller.exception.UserNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(ProductNotFoundException exc){
		ErrorResponse error = new ErrorResponse();
		error.setStatues(HttpStatus.NOT_FOUND.value());
		error.setMessage(exc.getMessage());
		error.setTimeResponse(System.currentTimeMillis());
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(Exception exc){
		ErrorResponse error = new ErrorResponse();
		error.setStatues(HttpStatus.BAD_REQUEST.value());
		error.setMessage(exc.getMessage());
		error.setTimeResponse(System.currentTimeMillis());
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST) ;
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(UserNotFoundException exc){
		ErrorResponse error = new ErrorResponse();
		error.setStatues(HttpStatus.NOT_FOUND.value());
		error.setMessage(exc.getMessage());
		error.setTimeResponse(System.currentTimeMillis());
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND) ;
	}

}
