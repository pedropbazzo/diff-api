package io.github.mariazevedo88.diffapi.controller;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.mariazevedo88.diffapi.model.JSONMessage;
import io.github.mariazevedo88.diffapi.model.ResultDiff;
import io.github.mariazevedo88.diffapi.repository.JSONMessageRepository;
import io.github.mariazevedo88.diffapi.service.ComparatorService;
import io.github.mariazevedo88.diffapi.service.DecodingService;
import io.github.mariazevedo88.diffapi.service.EncodingService;

@RestController
@RequestMapping(path = "/")
public class AppController {
	
	@Autowired
    private ComparatorService compareService;
	
	@Autowired
	private EncodingService encodingService; 
	
	@Autowired
	private DecodingService decodingService; 
	
	private JSONMessageRepository repository;
	
	private JSONMessage createMessage(Long id, String message) {
		
		JSONMessage jsonMessage = new JSONMessage();
		if(!encodingService.isEncodedBase64(message)) {
			message = encodingService.encodeToBase64(message.getBytes());
		}
		
		jsonMessage.setValue(message);
		jsonMessage.setId(id);
		
		return jsonMessage;
	}
	
	@GetMapping(path = "/")
	public List<JSONMessage> findAll() {
		repository = (JSONMessageRepository) JSONMessageRepository.getInstance();
		return repository.getAllMessages();
	}
	
	@GetMapping(path = "/v1/diff/left/all")
	public List<JSONMessage> findAllLeftMessages() {
		repository = (JSONMessageRepository) JSONMessageRepository.getInstance();
		return repository.getAllLeftMessages();
	}
	
	@GetMapping(path = "/v1/diff/right/all")
	public List<JSONMessage> findAllRightMessages() {
		repository = (JSONMessageRepository) JSONMessageRepository.getInstance();
		return repository.getAllRightMessages();
	}
	
	@PostMapping(path = "/v1/diff/{id}/left")
	public ResponseEntity<JSONMessage> saveLeftMessage(@PathVariable("id") long id, @RequestBody JSONObject message){
		JSONMessage finalMessage = createMessage(id, message.get("message").toString());
		try {
			repository = (JSONMessageRepository) JSONMessageRepository.getInstance();
			repository.saveLeftJSONMessage(finalMessage);
			return ResponseEntity.ok(finalMessage);
		} catch (Exception e) {
			return ResponseEntity.unprocessableEntity().body(finalMessage);
		}
	}

	@PostMapping(path = "/v1/diff/{id}/right")
	public ResponseEntity<JSONMessage> saveRightMessage(@PathVariable("id") long id, @RequestBody JSONObject message){
		JSONMessage finalMessage = createMessage(id, message.get("message").toString());
		try {
			repository = (JSONMessageRepository) JSONMessageRepository.getInstance();
			repository.saveRightJSONMessage(finalMessage);
			return ResponseEntity.ok(finalMessage);
		} catch (Exception e) {
			return ResponseEntity.unprocessableEntity().body(finalMessage);
		}
	}

	@GetMapping(path = "/v1/diff/{id}")
	public ResponseEntity<ResultDiff> compare(@PathVariable("id") long id) {
		ResultDiff result = compareService.compare(id);
		
		if(result == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(result);
	}
	
	@GetMapping(path = "/v1/diff/{id}/left/decodeString")
	public String getDecodedBase64LeftMessage(@PathVariable("id") long id) {
		repository = (JSONMessageRepository) JSONMessageRepository.getInstance();
		String message = repository.getLeftJSONMessage(id).getValue();
		return decodingService.decodeBase64ToString(message);
	}
	
	@GetMapping(path = "/v1/diff/{id}/right/decodeString")
	public String getDecodedBase64RightMessage(@PathVariable("id") long id) {
		repository = (JSONMessageRepository) JSONMessageRepository.getInstance();
		String message = repository.getRightJSONMessage(id).getValue();
		return decodingService.decodeBase64ToString(message);
	}
}
