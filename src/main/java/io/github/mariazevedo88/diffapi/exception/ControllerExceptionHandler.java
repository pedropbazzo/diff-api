package io.github.mariazevedo88.diffapi.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import io.github.mariazevedo88.diffapi.dto.response.Response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Class that implements a handler of exceptions and errors in the API, using {@ControllerAdvice} 
 * and sending the proper response to the client.
 * 
 * @author Mariana Azevedo
 * @since 08/03/2020
 * 
 * @param <T>
 */
@ControllerAdvice
public class ControllerExceptionHandler<T> {
	
	/**
	 * Method that handles with a InvalidBase64Exception and returns a Bad Request error
	 * with status code = 400.
	 * 
	 * @author Mariana Azevedo
	 * @since 26/04/2020
	 * 
	 * @param exception
	 * @return ResponseEntity<Response<T>>
	 */
	@ExceptionHandler(InvalidBase64Exception.class)
	protected ResponseEntity<Response<T>> handleBadRequest(InvalidBase64Exception exception) {
		
		Response<T> response = new Response<>();
		response.addErrorMsgToResponse(exception.getLocalizedMessage());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // 400
	}
	
	/**
	 * Method that handles with a MessageNotFoundException and returns a Not Found error 
	 * with status code = 404.
	 * 
	 * @author Mariana Azevedo
	 * @since 26/04/2020
	 * 
	 * @param exception
	 * @return ResponseEntity<Response<T>>
	 */
	@ExceptionHandler(MessageNotFoundException.class)
	protected ResponseEntity<Response<T>> handleNotFound(MessageNotFoundException exception) {
		
		Response<T> response = new Response<>();
		response.addErrorMsgToResponse(exception.getLocalizedMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); // 404
	}
	
	/**
	 * Method that handles with a DiffNotFoundException and returns an Not Found error 
	 * with status code = 404.
	 * 
	 * @author Mariana Azevedo
	 * @since 26/04/2020
	 * 
	 * @param exception
	 * @return ResponseEntity<Response<T>>
	 */
	@ExceptionHandler(DiffNotFoundException.class)
	protected ResponseEntity<Response<T>> handleNotFound(DiffNotFoundException exception) {
		
		Response<T> response = new Response<>();
		response.addErrorMsgToResponse(exception.getLocalizedMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); // 404
	}
  
	/**
	 * Method that handles with a HttpClientErrorException and returns an Conflict
	 * error with status code = 409.
	 * 
	 * @author Mariana Azevedo
	 * @since 26/04/2020
	 * 
	 * @param exception
	 * @return ResponseEntity<Response<T>>
	 */
	@ExceptionHandler(value = { HttpClientErrorException.Conflict.class })
    protected ResponseEntity<Response<T>> handleConflictException(HttpClientErrorException exception) {
		
		Response<T> response = new Response<>();
		response.addErrorMsgToResponse(exception.getLocalizedMessage());
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body(response); //409
    }
	
	/**
	 * Method that handles with a DiffException and returns an Unprocessable Entity error 
	 * with status code = 422.
	 * 
	 * @author Mariana Azevedo
	 * @since 26/04/2020
	 * 
	 * @param exception
	 * @return ResponseEntity<Response<T>>
	 */
	@ExceptionHandler(DiffException.class)
	protected ResponseEntity<Response<T>> handleUnprocessableEntity(DiffException exception) {
		
		Response<T> response = new Response<>();
		response.addErrorMsgToResponse(exception.getLocalizedMessage());
		
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response); // 422
	}
	
	/**
	 * Method that handles with a Exception and returns an Internal Server Error 
	 * with status code = 500.
	 * 
	 * @param exception
	 * @return ResponseEntity<Response<T>>
	 */
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Response<T>> handleGeneralError(Exception exception) {
		
		Response<T> response = new Response<>();
		response.addErrorMsgToResponse(exception.getLocalizedMessage());
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // 500
	}

}
