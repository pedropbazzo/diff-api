package io.github.mariazevedo88.diffapi.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.mariazevedo88.diffapi.dto.MessageDTO;
import io.github.mariazevedo88.diffapi.dto.ResultDiffDTO;
import io.github.mariazevedo88.diffapi.enumeration.ResultDiffEnum;
import io.github.mariazevedo88.diffapi.exception.InvalidBase64Exception;
import io.github.mariazevedo88.diffapi.exception.MessageNotFoundException;
import io.github.mariazevedo88.diffapi.model.Message;
import io.github.mariazevedo88.diffapi.model.Response;
import io.github.mariazevedo88.diffapi.model.ResultDiff;
import io.github.mariazevedo88.diffapi.service.MessageDiffService;
import io.github.mariazevedo88.diffapi.service.MessageService;
import io.github.mariazevedo88.diffapi.service.ResultDiffService;
import io.github.mariazevedo88.diffapi.util.MessageUtil;

/**
 * SpringBoot RestController that creates all service endpoints starting at '/v1/diff' for the API to function properly.
 * 
 * @author Mariana Azevedo
 * @since 08/03/2020
 *
 */
@RestController
@RequestMapping("/v1/diff/")
public class DiffController {
	
	private static final Logger logger = LoggerFactory.getLogger(DiffController.class);
	
	@Autowired
    private MessageService messageService;
	
	@Autowired
	private MessageDiffService diffService;
	
	@Autowired
	private ResultDiffService resultService;
	
	/**
	 * Method that fetches all messages at endpoints.
	 * 
	 * @author Mariana Azevedo
	 * @since 08/03/2020
	 * 
	 * @return ResponseEntity - 200
	 */
	@GetMapping(path = "/all")
	public ResponseEntity<Response<List<MessageDTO>>> findAll() throws MessageNotFoundException {
		
		Response<List<MessageDTO>> response = new Response<>();

		List<Message> message = messageService.findAll();
		response.setData(messageService.convertListEntityToListDTO(message));
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	/**
	 * Method that creates a message at the left endpoint, passing the id and message as a parameter.
	 * 
	 * @author Mariana Azevedo
	 * @since 23/07/2019
	 * 
	 * @param id
	 * @param message
	 * @return ResponseEntity - 201, if is created with success;
	 * 		   ResponseEntity - 200, if is an update on the Message object;
	 *         ResponseEntity - 400, if the request has errors or invalid base64 string;
	 *         ResponseEntity
	 * @throws Exception 
	 */
	@PostMapping(path = "/{id}/left")
	public ResponseEntity<Response<MessageDTO>> saveLeftMessage(@Valid @PathVariable("id") long id, 
			@Valid @RequestBody MessageDTO dto, BindingResult result) throws Exception{

		Response<MessageDTO> response = new Response<>();

		try {
			
			if(result.hasErrors()) {
				result.getAllErrors().forEach(e -> response.getErrors().add(e.getDefaultMessage()));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
			
			if(!MessageUtil.isEncodedBase64(dto.getLeftData())) {
				throw new InvalidBase64Exception();
			}
			
			String leftData = MessageUtil.encodeToBase64(dto.getLeftData().getBytes());
			dto.setLeftData(leftData);
			
			if(messageService.exist(id)) {
				updateLeftMessage(id, dto, response);
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}
			
			Message messageToCreate = messageService.convertDTOToEntity(dto);
			Message message = messageService.save(messageToCreate);
			response.setData(messageService.convertEntityToDTO(message));
			
			return ResponseEntity.status(HttpStatus.CREATED).body(response);

		} catch (Exception e) {
			throw new Exception();
		}
	}

	private void updateLeftMessage(long id, MessageDTO dto, Response<MessageDTO> response) {
		
		dto.setId(id);
		dto.setRightData(messageService.findById(id).get().getRightData());

		Message messageToUpdate = messageService.convertDTOToEntity(dto);
		Message message = messageService.updateLeftData(messageToUpdate);
		response.setData(messageService.convertEntityToDTO(message));
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
	 * @throws Exception 
	 */
	@PostMapping(path = "/{id}/right")
	public ResponseEntity<Response<MessageDTO>> saveRightMessage(@Valid @PathVariable("id") long id, 
			@Valid @RequestBody MessageDTO dto, BindingResult result) throws Exception{

		Response<MessageDTO> response = new Response<>();
		
		try {
			
			if(result.hasErrors()) {
				result.getAllErrors().forEach(e -> response.getErrors().add(e.getDefaultMessage()));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
			
			if(!MessageUtil.isEncodedBase64(dto.getRightData())) {
				throw new InvalidBase64Exception();
			}
			
			String rightData = MessageUtil.encodeToBase64(dto.getRightData().getBytes());
			dto.setRightData(rightData);
			
			if(messageService.exist(id)) {
				updateRightMessage(id, dto, response);
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}
			
			Message messageToCreate = messageService.convertDTOToEntity(dto);
			Message message = messageService.save(messageToCreate);
			response.setData(messageService.convertEntityToDTO(message));
			
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
			
		} catch (Exception e) {
			throw new Exception();
		}
	}

	private void updateRightMessage(long id, MessageDTO dto, Response<MessageDTO> response) {
		
		dto.setId(id);
		dto.setLeftData(messageService.findById(id).get().getLeftData());

		Message messageToUpdate = messageService.convertDTOToEntity(dto);
		Message message = messageService.updateRightData(messageToUpdate);
		response.setData(messageService.convertEntityToDTO(message));
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
	@GetMapping(path = "/{id}")
	public ResponseEntity<Response<ResultDiffDTO>> compare(@Valid @PathVariable("id") long id) {

		Response<ResultDiffDTO> response = new Response<>();
		
		try {
			
			Optional<Message> message = messageService.findById(id);
			ResultDiff resultDiff = resultService.compare(message.get());
			
			if(ResultDiffEnum.DIFFERENT.equals(resultDiff.getResult())) {
				resultDiff.setDiff(diffService.checkDiffBetweenWords(message.get()));
				diffService.saveMessageDiff(diffService.convertEntityToDTO(resultDiff.getDiff()));
			}

			resultDiff.setMessage(message.get());
			ResultDiff result = resultService.saveResultDiff(resultService.convertEntityToDTO(resultDiff));
			response.setData(resultService.convertEntityToDTO(result));
			
			return ResponseEntity.status(HttpStatus.OK).body(response);
		
		}catch (NullPointerException e) {
			logger.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
	
}
