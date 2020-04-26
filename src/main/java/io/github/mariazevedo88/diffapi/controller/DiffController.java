package io.github.mariazevedo88.diffapi.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.mariazevedo88.diffapi.dto.MessageDTO;
import io.github.mariazevedo88.diffapi.dto.ResultDiffDTO;
import io.github.mariazevedo88.diffapi.dto.response.Response;
import io.github.mariazevedo88.diffapi.enumeration.ResultDiffEnum;
import io.github.mariazevedo88.diffapi.exception.DiffException;
import io.github.mariazevedo88.diffapi.exception.DiffNotFoundException;
import io.github.mariazevedo88.diffapi.exception.InvalidBase64Exception;
import io.github.mariazevedo88.diffapi.exception.MessageNotFoundException;
import io.github.mariazevedo88.diffapi.model.Message;
import io.github.mariazevedo88.diffapi.model.ResultDiff;
import io.github.mariazevedo88.diffapi.service.MessageDiffService;
import io.github.mariazevedo88.diffapi.service.MessageService;
import io.github.mariazevedo88.diffapi.service.ResultDiffService;
import io.github.mariazevedo88.diffapi.util.DiffApiUtil;
import io.swagger.annotations.ApiOperation;

/**
 * SpringBoot RestController that creates all service endpoints starting at '/v1/diff' for 
 * the API to function properly.
 * 
 * @author Mariana Azevedo
 * @since 08/03/2020
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
	 * @throws MessageNotFoundException 
	 */
	@GetMapping(path = "/all", produces = { "application/hal+json" })
	public ResponseEntity<Response<List<ResultDiffDTO>>> findAllDiffs(@RequestHeader(value=DiffApiUtil.DIFF_API_VERSION_HEADER, 
		defaultValue="${api.version}") String apiVersion) throws DiffNotFoundException {
		
		Response<List<ResultDiffDTO>> response = new Response<>();
		List<ResultDiff> resultDiffs = resultService.findAll();
		
		if (resultDiffs.isEmpty()) {
			throw new DiffNotFoundException("There are no diffs registered in the database.");
		}
		
		List<ResultDiffDTO> resultDiffsDTO = new ArrayList<>();
		resultDiffs.stream().forEach(d -> resultDiffsDTO.add(resultService.convertEntityToDTO(d)));
		
		resultDiffsDTO.stream().forEach(dto -> {
			try {
				createResultDiffSelfLinkInCollections(apiVersion, dto);
			} catch (MessageNotFoundException e) {
				logger.error("There are no diffs registered in the database.");
			}
		});
		
		response.setData(resultDiffsDTO);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(DiffApiUtil.DIFF_API_VERSION_HEADER, apiVersion);
		return new ResponseEntity<>(response, headers, HttpStatus.OK);
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
	@PostMapping(path = "/{id}/left", produces = { "application/hal+json" })
	public ResponseEntity<Response<MessageDTO>> saveLeftMessage(@RequestHeader(value=DiffApiUtil.DIFF_API_VERSION_HEADER, 
		defaultValue="${api.version}") String apiVersion, @PathVariable("id") long id, @Valid @RequestBody MessageDTO dto, 
		BindingResult result) throws InvalidBase64Exception {

		Response<MessageDTO> response = new Response<>();

		if(result.hasErrors()) {
			result.getAllErrors().forEach(e -> response.addErrorMsgToResponse(e.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		
		if(!DiffApiUtil.isEncodedBase64(dto.getLeftData())) {
			throw new InvalidBase64Exception("The leftData " + dto.getLeftData() + " is a invalid Base64 String.");
		}
		
		String leftData = DiffApiUtil.encodeToBase64(dto.getLeftData().getBytes());
		dto.setLeftData(leftData);
		
		if(messageService.exist(id)) {
			updateLeftMessage(id, dto, response);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		
		Message messageToCreate = messageService.convertDTOToEntity(dto);
		Message message = messageService.save(messageToCreate);
		response.setData(messageService.convertEntityToDTO(message));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	private void updateLeftMessage(long id, MessageDTO dto, Response<MessageDTO> response) {
		
		Optional<Message> optionalMessage = messageService.findById(id);
		
		if(optionalMessage.isPresent()) {
			Message message = optionalMessage.get();
			dto.setId(message.getId());
			dto.setRightData(message.getRightData());
		}

		Message messageToUpdate = messageService.convertDTOToEntity(dto);
		Message message = messageService.updateRightData(messageToUpdate);
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
	@PostMapping(path = "/{id}/right", produces = { "application/hal+json" })
	public ResponseEntity<Response<MessageDTO>> saveRightMessage(@RequestHeader(value=DiffApiUtil.DIFF_API_VERSION_HEADER, 
		defaultValue="${api.version}") String apiVersion, @PathVariable("id") long id, @Valid @RequestBody MessageDTO dto, 
		BindingResult result) throws InvalidBase64Exception {

		Response<MessageDTO> response = new Response<>();
		
		if(result.hasErrors()) {
			result.getAllErrors().forEach(e -> response.addErrorMsgToResponse(e.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		
		if(!DiffApiUtil.isEncodedBase64(dto.getRightData())) {
			throw new InvalidBase64Exception("The rightData " + dto.getRightData() + " is a invalid Base64 String.");
		}
		
		String rightData = DiffApiUtil.encodeToBase64(dto.getRightData().getBytes());
		dto.setRightData(rightData);
		
		if(messageService.exist(id)) {
			updateRightMessage(id, dto, response);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		
		Message messageToCreate = messageService.convertDTOToEntity(dto);
		Message message = messageService.save(messageToCreate);
		response.setData(messageService.convertEntityToDTO(message));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	private void updateRightMessage(long id, MessageDTO dto, Response<MessageDTO> response) {
		
		Optional<Message> optionalMessage = messageService.findById(id);
		
		if(optionalMessage.isPresent()) {
			Message message = optionalMessage.get();
			dto.setId(message.getId());
			dto.setLeftData(message.getLeftData());
		}

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
	 * @throws DiffException 
	 * @throws DiffNotFoundException 
	 */
	@GetMapping(path = "/{id}", produces = { "application/hal+json" })
	public ResponseEntity<Response<ResultDiffDTO>> compare(@RequestHeader(value=DiffApiUtil.DIFF_API_VERSION_HEADER, 
		defaultValue="${api.version}") String apiVersion, @PathVariable("id") long id) throws DiffException, DiffNotFoundException {

		Response<ResultDiffDTO> response = new Response<>();
		
		Optional<Message> optionalMessage = messageService.findById(id);
		
		if(!optionalMessage.isPresent()) {
			throw new DiffException("There is no data to compare with the id=" + id);
		}
			
		Message message = optionalMessage.get();
		ResultDiff resultDiff = resultService.compare(message);
		
		if(ResultDiffEnum.DIFFERENT.equals(resultDiff.getResult())) {
			resultDiff.setDiff(diffService.checkDiffBetweenWords(message));
			diffService.saveMessageDiff(diffService.convertEntityToDTO(resultDiff.getDiff()));
		}
		
		resultDiff.setMessage(message);
		
		ResultDiffDTO diffDTO = resultService.convertEntityToDTO(resultDiff);
		ResultDiff result = resultService.saveResultDiff(diffDTO);
		
		diffDTO = resultService.convertEntityToDTO(result);
		
		createResultDiffSelfLink(apiVersion, diffDTO);
		response.setData(diffDTO);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(DiffApiUtil.DIFF_API_VERSION_HEADER, apiVersion);
		return new ResponseEntity<>(response, headers, HttpStatus.OK);
	}
	
	/**
	 * Method that search a transactions by the id.
	 * 
	 * @param apiVersion
	 * @param messageId
	 * @return ResponseEntity with a Response<MessageDTO> object and the HTTP status
	 * 
	 * HTTP Status:
	 * 
	 * 200 - OK: Everything worked as expected.
	 * 400 - Bad Request: The request was unacceptable, often due to missing a required parameter.
	 * 404 - Not Found: The requested resource doesn't exist.
	 * 409 - Conflict: The request conflicts with another request (perhaps due to using the same idempotent key).
	 * 429 - Too Many Requests: Too many requests hit the API too quickly. We recommend an exponential backoff of your requests.
	 * 500, 502, 503, 504 - Server Errors: something went wrong on API end (These are rare).
	 * @throws MessageNotFoundException 
	 * 
	 * @throws TransactionNotFoundException
	 */
	@GetMapping(value = "/{id}/left")
	@ApiOperation(value = "Route to find a left message by your id in the API")
	public ResponseEntity<Response<MessageDTO>> getLeftMessage(@RequestHeader(value=DiffApiUtil.DIFF_API_VERSION_HEADER, 
		defaultValue="${api.version}") String apiVersion, @PathVariable("id") Long messageId) throws MessageNotFoundException {
		
		return getMessageById(apiVersion, messageId);
	}
	
	/**
	 * Method that search a transactions by the id.
	 * 
	 * @param apiVersion
	 * @param messageId
	 * @return ResponseEntity with a Response<MessageDTO> object and the HTTP status
	 * 
	 * HTTP Status:
	 * 
	 * 200 - OK: Everything worked as expected.
	 * 400 - Bad Request: The request was unacceptable, often due to missing a required parameter.
	 * 404 - Not Found: The requested resource doesn't exist.
	 * 409 - Conflict: The request conflicts with another request (perhaps due to using the same idempotent key).
	 * 429 - Too Many Requests: Too many requests hit the API too quickly. We recommend an exponential backoff of your requests.
	 * 500, 502, 503, 504 - Server Errors: something went wrong on API end (These are rare).
	 * @throws MessageNotFoundException 
	 * 
	 * @throws TransactionNotFoundException
	 */
	@GetMapping(value = "/{id}/right")
	@ApiOperation(value = "Route to find a right message by your id in the API")
	public ResponseEntity<Response<MessageDTO>> getRightMessage(@RequestHeader(value=DiffApiUtil.DIFF_API_VERSION_HEADER, 
		defaultValue="${api.version}") String apiVersion, @PathVariable("id") Long messageId) throws MessageNotFoundException {
		
		return getMessageById(apiVersion, messageId);
	}

	private ResponseEntity<Response<MessageDTO>> getMessageById(String apiVersion, Long transactionId)
			throws MessageNotFoundException {
		
		Response<MessageDTO> response = new Response<>();
		Optional<Message> optionalMessage = messageService.findById(transactionId);
		
		if (!optionalMessage.isPresent()) {
			throw new MessageNotFoundException("Message id=" + transactionId + " not found");
		}
		
		Message message = optionalMessage.get();
		MessageDTO dto = messageService.convertEntityToDTO(message);
		
		createMessageSelfLink(message, dto);
		response.setData(dto);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(DiffApiUtil.DIFF_API_VERSION_HEADER, apiVersion);
		return new ResponseEntity<>(response, headers, HttpStatus.OK);
	}
	
	/**
	 * Method that search a transactions by the id.
	 * 
	 * @param apiVersion
	 * @param diffId
	 * @return ResponseEntity with a Response<TransactionDTO> object and the HTTP status
	 * 
	 * HTTP Status:
	 * 
	 * 200 - OK: Everything worked as expected.
	 * 400 - Bad Request: The request was unacceptable, often due to missing a required parameter.
	 * 404 - Not Found: The requested resource doesn't exist.
	 * 409 - Conflict: The request conflicts with another request (perhaps due to using the same idempotent key).
	 * 429 - Too Many Requests: Too many requests hit the API too quickly. We recommend an exponential backoff of your requests.
	 * 500, 502, 503, 504 - Server Errors: something went wrong on API end (These are rare).
	 * @throws MessageNotFoundException 
	 * @throws DiffNotFoundException 
	 * 
	 * @throws TransactionNotFoundException
	 */
	@GetMapping(value = "/byId/{id}")
	@ApiOperation(value = "Route to find a diff by your id in the API")
	public ResponseEntity<Response<ResultDiffDTO>> getDiffById(@RequestHeader(value=DiffApiUtil.DIFF_API_VERSION_HEADER, 
		defaultValue="${api.version}") String apiVersion, @PathVariable("id") Long diffId) throws DiffNotFoundException {
		
		Response<ResultDiffDTO> response = new Response<>();
		Optional<ResultDiff> resultDiff = resultService.findById(diffId);
		
		if (!resultDiff.isPresent()) {
			throw new DiffNotFoundException("Diff id=" + diffId + " not found");
		}
		
		ResultDiff diff = resultDiff.get();
		ResultDiffDTO dto = resultService.convertEntityToDTO(diff);
		
		createResultDiffSelfLink(apiVersion, dto);
		response.setData(dto);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add(DiffApiUtil.DIFF_API_VERSION_HEADER, apiVersion);
		return new ResponseEntity<>(response, headers, HttpStatus.OK);
	}
	
	/**
	 * Method that creates a self link to message object
	 * 
	 * @author Mariana Azevedo
	 * @since 25/04/2020
	 * 
	 * @param message
	 * @param messageDTO
	 */
	private void createMessageSelfLink(Message message, MessageDTO messageDTO) {
		Link selfLink = WebMvcLinkBuilder.linkTo(DiffController.class).slash(message.getId()).withSelfRel();
		messageDTO.add(selfLink);
	}
	
	/**
	 * Method that creates a self link to message object
	 * 
	 * @author Mariana Azevedo
	 * @since 25/04/2020
	 * 
	 * @param apiVersion
	 * @param resultDiffDTO
	 * @throws DiffNotFoundException 
	 */
	private void createResultDiffSelfLink(String apiVersion, ResultDiffDTO resultDiffDTO) throws DiffNotFoundException {
		Link selfLink = linkTo(methodOn(DiffController.class).getDiffById(apiVersion, resultDiffDTO.getId())).withSelfRel();
		resultDiffDTO.add(selfLink);
	}
	
	/**
	 * Method that creates a self link in a collection of transactions
	 * 
	 * @author Mariana Azevedo
	 * @since 25/04/2020
	 * 
	 * @param apiVersion
	 * @param resultDiffDTO
	 * @throws MessageNotFoundException 
	 */
	private void createResultDiffSelfLinkInCollections(String apiVersion, final ResultDiffDTO resultDiffDTO) throws MessageNotFoundException {
		Link selfLink = linkTo(methodOn(DiffController.class).getLeftMessage(apiVersion, resultDiffDTO.getId())).withSelfRel();
		resultDiffDTO.add(selfLink);
	}
	
}
