package io.github.mariazevedo88.diffapi.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

import lombok.extern.slf4j.Slf4j;

/**
 * Class that implements an Spring controller advisor for the API
 * 
 * @author Mariana Azevedo
 * @since 08/03/2020
 */
@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {
	
	@ResponseStatus(HttpStatus.NOT_FOUND) // 404
	@ExceptionHandler(MessageNotFoundException.class)
	public void handleNotFound(MessageNotFoundException ex) {
		log.error("Requested message not found" + ex.getLocalizedMessage());
	}
  
	@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
	@ExceptionHandler(InvalidBase64Exception.class)
	public void handleBadRequest(InvalidBase64Exception ex) {
		log.error("Invalid Json Base64 supplied in request" + ex.getLocalizedMessage());
	}
	
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) // 422
	@ExceptionHandler(DiffException.class)
	public void handleUnprocessableEntity(DiffException ex) {
		log.error("Diff cannot be processed." + ex.getLocalizedMessage());
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
	@ExceptionHandler(Exception.class)
	public void handleGeneralError(Exception ex) {
		log.error("An error occurred processing request" + ex.getLocalizedMessage());
	}

}
