package io.github.mariazevedo88.diffapi.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.github.mariazevedo88.diffapi.dto.response.Response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Class that implements an Spring controller advisor for the API
 * 
 * @author Mariana Azevedo
 * @since 08/03/2020
 */
@ControllerAdvice
public class ControllerExceptionHandler<T> {
	
	@ExceptionHandler(MessageNotFoundException.class)
	public ResponseEntity<Response<T>> handleNotFound(MessageNotFoundException ex) {
		
		Response<T> response = new Response<>();
		response.addErrorMsgToResponse(ex.getLocalizedMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); // 404
	}
	
	
	@ExceptionHandler(DiffNotFoundException.class)
	public ResponseEntity<Response<T>> handleNotFound(DiffNotFoundException ex) {
		
		Response<T> response = new Response<>();
		response.addErrorMsgToResponse(ex.getLocalizedMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); // 404
	}
  
	@ExceptionHandler(InvalidBase64Exception.class)
	public ResponseEntity<Response<T>> handleBadRequest(InvalidBase64Exception ex) {
		
		Response<T> response = new Response<>();
		response.addErrorMsgToResponse(ex.getLocalizedMessage());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // 400
	}
	
	@ExceptionHandler(DiffException.class)
	public ResponseEntity<Response<T>> handleUnprocessableEntity(DiffException ex) {
		
		Response<T> response = new Response<>();
		response.addErrorMsgToResponse(ex.getLocalizedMessage());
		
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response); // 422
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Response<T>> handleGeneralError(Exception ex) {
		
		Response<T> response = new Response<>();
		response.addErrorMsgToResponse(ex.getLocalizedMessage());
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // 500
	}

}
