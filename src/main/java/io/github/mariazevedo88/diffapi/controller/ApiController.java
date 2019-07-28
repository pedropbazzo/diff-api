package io.github.mariazevedo88.diffapi.controller;

import java.net.URI;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.mariazevedo88.diffapi.model.JSONMessage;
import io.github.mariazevedo88.diffapi.model.ResultDiff;
import io.github.mariazevedo88.diffapi.repository.JSONMessageRepository;
import io.github.mariazevedo88.diffapi.service.ComparatorService;
import io.github.mariazevedo88.diffapi.service.DecodingService;
import io.github.mariazevedo88.diffapi.service.EncodingService;

/**
 * SpringBoot RestController that creates all service endpoints starting at '/v1/diff' for the API to function properly.
 * 
 * @author Mariana Azevedo
 * @since 23/07/2019
 *
 */
@RestController
@RequestMapping(path = "/")
public class ApiController {
	
	private static final Logger logger = Logger.getLogger(ApiController.class);
	
	@Autowired
    private ComparatorService compareService;
	
	@Autowired
	private EncodingService encodingService; 
	
	@Autowired
	private DecodingService decodingService;
	
	private JSONMessageRepository repository;
	
	/**
	 * Method that creates a JSONMessage, checking if the passed String is in base64.
	 * 
	 * @author Mariana Azevedo
	 * @since 23/07/2019
	 * 
	 * @param id
	 * @param message
	 * @return JSONMessage
	 */
	private JSONMessage createMessage(Long id, String message) {
		
		JSONMessage jsonMessage = new JSONMessage();
		if(!encodingService.isEncodedBase64(message)) {
			message = encodingService.encodeToBase64(message.getBytes());
		}
		
		jsonMessage.setValue(message);
		jsonMessage.setId(id);
		
		return jsonMessage;
	}
	
	/**
	 * Method that fetches all messages at endpoints.
	 * 
	 * @author Mariana Azevedo
	 * @since 25/07/2019
	 * 
	 * @return ResponseEntity - 200, if has messages or 404 if hasn't.
	 */
	@GetMapping(path = "/")
	public ResponseEntity<List<JSONMessage>> findAll() {
		repository = (JSONMessageRepository) JSONMessageRepository.getInstance();

		if(repository.getAllMessages().isEmpty()) {
			return ResponseEntity.notFound().build(); 
		}
		
		return ResponseEntity.ok(repository.getAllMessages());
	}
	
	/**
	 * Method that cleans all messages at endpoints.
	 * 
	 * @author Mariana Azevedo
	 * @since 28/07/2019
	 * 
	 * @return ResponseEntity - 200.
	 */
	@GetMapping(path = "/v1/diff/cleanAllMessages")
	public ResponseEntity<Boolean> cleanAllMessages() {
		repository = (JSONMessageRepository) JSONMessageRepository.getInstance();
		return ResponseEntity.ok(repository.cleanAllMessages());
	}
	
	/**
	 * Method that fetches all left messages at the left endpoints.
	 * 
	 * @author Mariana Azevedo
	 * @since 25/07/2019
	 * 
	 * @return ResponseEntity - 200, if has messages or 404 if hasn't.
	 */
	@GetMapping(path = "/v1/diff/left/all")
	public ResponseEntity<List<JSONMessage>> findAllLeftMessages() {
		repository = (JSONMessageRepository) JSONMessageRepository.getInstance();
		
		if(repository.getAllLeftMessages().isEmpty()) {
			return ResponseEntity.notFound().build(); 
		}
		
		return ResponseEntity.ok(repository.getAllLeftMessages());
	}
	
	/**
	 * Method that fetches all right messages at the right endpoints.
	 * 
	 * @author Mariana Azevedo
	 * @since 25/07/2019
	 * 
	 * @return ResponseEntity - 200, if has messages or 404 if hasn't.
	 */
	@GetMapping(path = "/v1/diff/right/all")
	public ResponseEntity<List<JSONMessage>> findAllRightMessages() {
		repository = (JSONMessageRepository) JSONMessageRepository.getInstance();
		
		if(repository.getAllRightMessages().isEmpty()) {
			return ResponseEntity.notFound().build(); 
		}
		
		return ResponseEntity.ok(repository.getAllRightMessages());
	}
	
	/**
	 * Method that creates a message at the left endpoint, passing the id and message as a parameter.
	 * 
	 * @author Mariana Azevedo
	 * @since 23/07/2019
	 * 
	 * @param id
	 * @param message
	 * @return ResponseEntity - 201, if is created with success or 500 if isn't.
	 */
	@PostMapping(path = "/v1/diff/{id}/left")
	public ResponseEntity<JSONMessage> createLeftJSONMessage(@PathVariable("id") long id, @RequestBody JSONObject message){
		try {
			JSONMessage finalMessage = createMessage(id, message.get("message").toString());
			repository = (JSONMessageRepository) JSONMessageRepository.getInstance();
			repository.createLeftJSONMessage(finalMessage);
			
			logger.info(finalMessage.toString());
			
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/v1/diff/{id}/left").buildAndExpand(id).toUri();
			return ResponseEntity.created(location).body(finalMessage);
			
		} catch (Exception e) {
			logger.error("Message is not a valid string." + e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	/**
	 * Method that creates a message at the right endpoint, passing the id and message as a parameter.
	 * 
	 * @author Mariana Azevedo
	 * @since 23/07/2019
	 * 
	 * @param id
	 * @param message
	 * @return ResponseEntity - 201, if is created with success or 500 if isn't.
	 */
	@PostMapping(path = "/v1/diff/{id}/right")
	public ResponseEntity<JSONMessage> createRightJSONMessage(@PathVariable("id") long id, @RequestBody JSONObject message){
		try {
			JSONMessage finalMessage = createMessage(id, message.get("message").toString());
			repository = (JSONMessageRepository) JSONMessageRepository.getInstance();
			repository.createRightJSONMessage(finalMessage);
			
			logger.info(finalMessage.toString());

			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/v1/diff/{id}/right").buildAndExpand(id).toUri();
			return ResponseEntity.created(location).body(finalMessage);
			
		} catch (Exception e) {
			logger.error("Message is not a valid string." + e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	/**
	 * Method that calls the string comparison service, passing parameter id.
	 * 
	 * @author Mariana Azevedo
	 * @since 23/07/2019
	 * 
	 * @param id
	 * @return ResponseEntity - 200, if the id exists or 500 if isn't.
	 */
	@GetMapping(path = "/v1/diff/{id}")
	public ResponseEntity<ResultDiff> compare(@PathVariable("id") long id) {
		ResultDiff result = null;
		try {
			result = compareService.compare(id);
			return ResponseEntity.ok(result);
		}catch (NullPointerException e) {
			logger.error(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
		}
	}
	
	/**
	 * Method that decodes a string in base64 in the left endpoint.
	 * 
	 * @author Mariana Azevedo
	 * @since 23/07/2019
	 * 
	 * @param id
	 * @return ResponseEntity - 200, if is decoded with success or 500 if isn't.
	 */
	@GetMapping(path = "/v1/diff/{id}/left/decodeString")
	public ResponseEntity<String> getDecodedBase64LeftMessage(@PathVariable("id") long id) {
		
		String message = null;
		
		try {
			repository = (JSONMessageRepository) JSONMessageRepository.getInstance();

			message = repository.getLeftJSONMessage(id).getValue();
			String decodeBase64ToString = decodingService.decodeBase64ToString(message);
			
			return ResponseEntity.ok(decodeBase64ToString);
			
		}catch (Exception e){
			logger.error("Error on decode the message: " + message + " " + e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}
	
	/**
	 * Method that decodes a string in base64 in the right endpoint.
	 * 
	 * @author Mariana Azevedo
	 * @since 23/07/2019
	 * 
	 * @param id
	 * @return ResponseEntity - 200, if is decoded with success or 500 if isn't.
	 */
	@GetMapping(path = "/v1/diff/{id}/right/decodeString")
	public ResponseEntity<String> getDecodedBase64RightMessage(@PathVariable("id") long id) {
		
		String message = null;
		
		try {
			repository = (JSONMessageRepository) JSONMessageRepository.getInstance();
			
			message = repository.getRightJSONMessage(id).getValue();
			String decodeBase64ToString = decodingService.decodeBase64ToString(message);
			
			return ResponseEntity.ok(decodeBase64ToString);
			
		}catch (Exception e){
			logger.error("Error on decode the message: " + message + " " + e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
		}
	}
	
	/**
	 * Method that decodes a string in base64 in the left endpoint.
	 * 
	 * @author Mariana Azevedo
	 * @since 23/07/2019
	 * 
	 * @param id
	 * @return ResponseEntity - 200, if is decoded with success or 500 if isn't.
	 */
	@PostMapping(path = "/v1/diff/{id}/left/encodeString")
	public ResponseEntity<String> getEncodedBase64LeftMessage(@PathVariable("id") long id, @RequestBody JSONObject message) {
		
		try {
			String leftMessage = message.get("message").toString();
			if(!encodingService.isEncodedBase64(leftMessage)) {
				leftMessage = encodingService.encodeToBase64(leftMessage.getBytes());
			}
			return ResponseEntity.ok(leftMessage);
			
		}catch (NullPointerException e){
			logger.error("Error on encode the message: " + message + " " + e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	/**
	 * Method that decodes a string in base64 in the left endpoint.
	 * 
	 * @author Mariana Azevedo
	 * @since 23/07/2019
	 * 
	 * @param id
	 * @return ResponseEntity - 200, if is decoded with success or 500 if isn't.
	 */
	@PostMapping(path = "/v1/diff/{id}/right/encodeString")
	public ResponseEntity<String> getEncodedBase64RightMessage(@PathVariable("id") long id, @RequestBody JSONObject message) {
		
		try {
			String rightMessage = message.get("message").toString();
			if(!encodingService.isEncodedBase64(rightMessage)) {
				rightMessage = encodingService.encodeToBase64(rightMessage.getBytes());
			}
			return ResponseEntity.ok(rightMessage);
			
		}catch (NullPointerException e){
			logger.error("Error on encode the message: " + message + " " + e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
}
