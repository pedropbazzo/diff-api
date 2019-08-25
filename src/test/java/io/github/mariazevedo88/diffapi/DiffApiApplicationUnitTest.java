package io.github.mariazevedo88.diffapi;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.github.mariazevedo88.diffapi.controller.ApiController;
import io.github.mariazevedo88.diffapi.enumeration.ResultDiffEnum;
import io.github.mariazevedo88.diffapi.model.JSONMessage;
import io.github.mariazevedo88.diffapi.model.MessageDiff;
import io.github.mariazevedo88.diffapi.model.ResultDiff;
import io.github.mariazevedo88.diffapi.repository.JSONMessageRepository;
import io.github.mariazevedo88.diffapi.service.ComparatorService;
import io.github.mariazevedo88.diffapi.service.EncodingService;

/**
 * Class that implements API unit tests
 * 
 * @author Mariana Azevedo
 * @since 27/07/2019
 *
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("DiffApiApplicationUnitTest")
@TestInstance(Lifecycle.PER_CLASS)
public class DiffApiApplicationUnitTest {
	
	@Autowired
    private ApiController controller;
	
	@Autowired
	private ComparatorService comparatorService;
	
	@Autowired
	private EncodingService encodingService;
	
	private JSONMessageRepository repository;
	
	@BeforeAll
	public void setUp() {
		repository = (JSONMessageRepository) JSONMessageRepository.getInstance();
	}
	
	@Test
	@DisplayName("Verify if controller exists on context loads")
	public void shouldReturnNotNullController() throws Exception {
		assertNotNull(controller);
	}
	
	@Test
	@DisplayName("Verify if repository was created")
	public void shouldReturnNotNullRepository() {
		assertNotNull(repository);
	}
	
	@Test
	@DisplayName("Verify if JSON Base64 was created in the left endpoint")
	public void shouldCreateJSONBase64InLeftEndpoint() throws Exception {
		
		long id = 1;
		
		Map<String, String> map = new HashMap<>();
		map.put("message", "SGVsbG8gV29ybGQ=");
		
		ResponseEntity<JSONMessage> saveLeftMessage = controller.createLeftJSONMessage(id, new JSONObject(map));
		
		assertEquals(map.get("message"), saveLeftMessage.getBody().getValue());
	}
	
	@Test
	@DisplayName("Verify if JSON Base64 was created in the right endpoint")
	public void shouldCreateJSONBase64InRightEndpoint() throws Exception {
		
		long id = 1;
		
		Map<String, String> map = new HashMap<>();
		map.put("message", "SGVsbG8gV29ybGQ=");
		
		ResponseEntity<JSONMessage> saveRightMessage = controller.createRightJSONMessage(id, new JSONObject(map));
		
		assertEquals(map.get("message"), saveRightMessage.getBody().getValue());
	}
	
	@Test
	@DisplayName("Checks whether a message in the left endpoint with the same id will have its values updated.")
	public void shouldLeftMessageWithSameIdBeReplaced() throws Exception {
		
		long id = 2;
		
		Map<String, String> originalJSON = new HashMap<>();
		originalJSON.put("message", "SGVsbG8gV29ybGQ=");
		controller.createLeftJSONMessage(id, new JSONObject(originalJSON));
		
		JSONMessage leftMessageOriginal = JSONMessageRepository.getInstance().getLeftJSONMessage(id);
		
		Map<String, String> replacedJSON = new HashMap<>();
		replacedJSON.put("message", "SGVsbG8gV29ybGQh");
		controller.createLeftJSONMessage(id, new JSONObject(replacedJSON));
		
		JSONMessage leftMessageReplaced = JSONMessageRepository.getInstance().getLeftJSONMessage(id);
		
		assertNotNull(leftMessageOriginal);
		assertNotNull(leftMessageReplaced);
		assertEquals(leftMessageOriginal.getId(), leftMessageReplaced.getId(), "Same Ids");
		assertNotEquals(leftMessageOriginal.getValue(), leftMessageReplaced.getValue(), "Different Values");
		
	}
	
	@Test
	@DisplayName("Checks whether a message in the right endpoint with the same id will have its values updated.")
	public void shouldRightMessageWithSameIdBeReplaced() throws Exception {
		
		long id = 2;
		
		Map<String, String> originalJSON = new HashMap<>();
		originalJSON.put("message", "SGVsbG8gV29ybGQ=");
		controller.createRightJSONMessage(id, new JSONObject(originalJSON));
		
		JSONMessage rightMessageOriginal = JSONMessageRepository.getInstance().getRightJSONMessage(id);
		
		Map<String, String> replacedJSON = new HashMap<>();
		replacedJSON.put("message", "SGVsbG8gV29ybGQh");
		controller.createRightJSONMessage(id, new JSONObject(replacedJSON));
		
		JSONMessage rightMessageReplaced = JSONMessageRepository.getInstance().getRightJSONMessage(id);
		
		assertNotNull(rightMessageOriginal);
		assertNotNull(rightMessageReplaced);
		assertEquals(rightMessageOriginal.getId(), rightMessageReplaced.getId(), "Same Ids");
		assertNotEquals(rightMessageOriginal.getValue(), rightMessageReplaced.getValue(), "Different Values");
		
	}
	
	@Test
	@DisplayName("Checks if endpoint message comparison returns equal.")
	public void shouldTheComparisonResultBeEqual() throws Exception {
		
		long id = 3;
		
		Map<String, String> message = new HashMap<>();
		message.put("message", "SGVsbG8gV29ybGQ=");
		controller.createLeftJSONMessage(id, new JSONObject(message));
		controller.createRightJSONMessage(id, new JSONObject(message));
		
		ResultDiff result = comparatorService.compare(id);
		assertNotNull(result);
		assertEquals(ResultDiffEnum.EQUAL, result.getResult());
		assertNull(result.getDiffs());
	}
	
	@Test
	@DisplayName("Checks if endpoint message comparison returns different by size.")
	public void shouldTheComparisonResultBeDifferentSize() throws Exception {
		
		long id = 4;
		
		Map<String, String> leftMessage = new HashMap<>();
		leftMessage.put("message", "TWFyaWFuYQ==");
		
		Map<String, String> rightMessage = new HashMap<>();
		rightMessage.put("message", "THVjYXM=");
		
		controller.createLeftJSONMessage(id, new JSONObject(leftMessage));
		controller.createRightJSONMessage(id, new JSONObject(rightMessage));
		
		ResultDiff result = comparatorService.compare(id);
		assertNotNull(result);
		assertEquals(ResultDiffEnum.DIFFERENT_SIZE, result.getResult());
		assertNull(result.getDiffs());
	}
	
	@Test
	@DisplayName("Checks if endpoint message comparison returns different in the end of the string.")
	public void shouldTheComparisonResultBeDifferentInEndOfString() throws Exception {
		
		long id = 5;
		
		Map<String, String> leftMessage = new HashMap<>();
		leftMessage.put("message", "TWFyaWFuYQ==");
		
		Map<String, String> rightMessage = new HashMap<>();
		rightMessage.put("message", "TWFyaWFuZQ==");
		
		controller.createLeftJSONMessage(id, new JSONObject(leftMessage));
		controller.createRightJSONMessage(id, new JSONObject(rightMessage));
		
		Object[] diffList = {new MessageDiff(8, 1)};
		
		ResultDiff result = comparatorService.compare(id);
		assertNotNull(result);
		assertEquals(ResultDiffEnum.DIFFERENT, result.getResult());
		assertNotNull(result.getDiffs());
		assertArrayEquals(diffList, result.getDiffs().toArray());
	}
	
	@Test
	@DisplayName("Checks if endpoint message comparison returns different in the beginning of the string.")
	public void shouldTheComparisonResultBeDifferentInBeginningOfString() throws Exception {
		
		long id = 6;
		
		Map<String, String> leftMessage = new HashMap<>();
		leftMessage.put("message", "TWFyaWFuYQ==");
		
		Map<String, String> rightMessage = new HashMap<>();
		rightMessage.put("message", "SnVsaWFuYQ==");
		
		controller.createLeftJSONMessage(id, new JSONObject(leftMessage));
		controller.createRightJSONMessage(id, new JSONObject(rightMessage));
		
		Object[] diffList = {new MessageDiff(0, 4)};
		
		ResultDiff result = comparatorService.compare(id);
		assertNotNull(result);
		assertEquals(ResultDiffEnum.DIFFERENT, result.getResult());
		assertNotNull(result.getDiffs());
		assertArrayEquals(diffList, result.getDiffs().toArray());
	}
	
	@Test
	@DisplayName("Checks if endpoint message comparison returns completly different strings.")
	public void shouldTheComparisonResultBeCompletlyDifferent() throws Exception {
		
		long id = 7;
		
		Map<String, String> leftMessage = new HashMap<>();
		leftMessage.put("message", "QXBwbGU=");
		
		Map<String, String> rightMessage = new HashMap<>();
		rightMessage.put("message", "SnVpY2U=");
		
		controller.createLeftJSONMessage(id, new JSONObject(leftMessage));
		controller.createRightJSONMessage(id, new JSONObject(rightMessage));
		
		Object[] diffList = {new MessageDiff(0, 6)};
		
		ResultDiff result = comparatorService.compare(id);
		assertNotNull(result);
		assertEquals(ResultDiffEnum.DIFFERENT, result.getResult());
		assertNotNull(result.getDiffs());
		assertArrayEquals(diffList, result.getDiffs().toArray());
	}
	
	@Test
	@DisplayName("Checks if endpoint message comparison returns completly different strings.")
	public void shouldReturnADecodedStringFromLeftEndpoint() throws Exception {
		
		long id = 7;
		
		Map<String, String> leftMessage = new HashMap<>();
		leftMessage.put("message", "QXBwbGU=");
		
		controller.createLeftJSONMessage(id, new JSONObject(leftMessage));
		ResponseEntity<String> decodedBase64LeftMessage = controller.getDecodedBase64LeftMessage(id);
		
		assertNotNull(decodedBase64LeftMessage);
		assertEquals("Apple", decodedBase64LeftMessage.getBody());
	}
	
	@Test
	@DisplayName("Checks if endpoint message comparison returns completly different strings.")
	public void shouldReturnADecodedStringFromRightEndpoint() throws Exception {
		
		long id = 7;
		
		Map<String, String> rightMessage = new HashMap<>();
		rightMessage.put("message", "SnVpY2U=");
		
		controller.createRightJSONMessage(id, new JSONObject(rightMessage));
		ResponseEntity<String> decodedBase64RightMessage = controller.getDecodedBase64RightMessage(id);
		
		assertNotNull(decodedBase64RightMessage);
		assertEquals("Juice", decodedBase64RightMessage.getBody());
	}
	
	@Test
	@DisplayName("Verify if string not in Base64 is corrected and created in the left endpoint")
	public void shouldCreateAndEncodeAStringInJSONBase64InLeftEndpoint() throws Exception {
		
		long id = 8;
		
		Map<String, String> map = new HashMap<>();
		map.put("message", "Hello World");
		
		String stringEncodedBase64 = encodingService.encodeToBase64(map.get("message").getBytes());
		
		ResponseEntity<JSONMessage> saveLeftMessage = controller.createLeftJSONMessage(id, new JSONObject(map));
		
		assertEquals(stringEncodedBase64, saveLeftMessage.getBody().getValue());
	}
	
	@Test
	@DisplayName("Verify if string not in Base64 is corrected and created in the right endpoint")
	public void shouldCreateAndEncodeAStringInJSONBase64InRightEndpoint() throws Exception {
		
		long id = 8;
		
		Map<String, String> map = new HashMap<>();
		map.put("message", "Hello World!");
		
		String stringEncodedBase64 = encodingService.encodeToBase64(map.get("message").getBytes());
		
		ResponseEntity<JSONMessage> saveRightMessage = controller.createLeftJSONMessage(id, new JSONObject(map));
		
		assertEquals(stringEncodedBase64, saveRightMessage.getBody().getValue());
	}
	
	@Test
	@DisplayName("Checks if endpoint message comparison returns null, because the message on right endpoint is null.")
	public void shouldTheComparisonResultBeNullInRightEndpoint() throws Exception {
		 assertThrows(NullPointerException.class,()->{
			 long id = 9;
			 
			 Map<String, String> message = new HashMap<>();
			 message.put("message", "SGVsbG8gV29ybGQ=");
			 controller.createLeftJSONMessage(id, new JSONObject(message));
			 
			 comparatorService.compare(id);
		 });
	}
	
	@Test
	@DisplayName("Checks if endpoint message comparison returns null, because the message on left endpoint is null.")
	public void shouldTheComparisonResultBeNullInLeftEndpoint() throws Exception {
		 assertThrows(NullPointerException.class,()->{
			 long id = 10;
			 
			 Map<String, String> message = new HashMap<>();
			 message.put("message", "SGVsbG8gV29ybGQ=");
			 controller.createRightJSONMessage(id, new JSONObject(message));
			 
			 comparatorService.compare(id);
		 });
	}
	
	@Test
	@DisplayName("Checks if endpoint message comparison returns null, because the message is null in both endpoints.")
	public void shouldTheComparisonResultBeNullInBothEndpoints() throws Exception {
		 assertThrows(NullPointerException.class,()->{
			 long id = 11;
			 comparatorService.compare(id);
		 });
	}
	
	@Test
	@DisplayName("Verify if API respond error on create a JSON Base64 from a null String in left endpoint.")
	public void shouldGetErrorOnCreateJSONBase64InLeftEndpoint() throws Exception {
		
		 Map<String, String> map = new HashMap<>();
		 map.put("message", null);
		 
		 ResponseEntity<JSONMessage> createLeftJSONMessage = controller.createLeftJSONMessage(12, new JSONObject(map));
		 assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, createLeftJSONMessage.getStatusCode());
	}
	
	@Test
	@DisplayName("Verify if API respond error on create a JSON Base64 from a null String in left endpoint")
	public void shouldGetErrorOnCreateJSONBase64InRightEndpoint() throws Exception {
		
		Map<String, String> map = new HashMap<>();
		map.put("message", null);
		 
		ResponseEntity<JSONMessage> createRightJSONMessage = controller.createRightJSONMessage(12, new JSONObject(map));
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, createRightJSONMessage.getStatusCode());
	}
	
	@Test
	@DisplayName("Verify if API respond error on decode a JSON Base64 from a null String in right endpoint")
	public void shouldGetErrorOnDecodeJSONBase64InLeftEndpoint() throws Exception {
		
		ResponseEntity<String> decodedBase64LeftMessage = controller.getDecodedBase64RightMessage(99);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, decodedBase64LeftMessage.getStatusCode());
	}
	
	@Test
	@DisplayName("Verify if API respond error on decode a JSON Base64 from a null String in right endpoint")
	public void shouldGetErrorOnDecodeJSONBase64InRightEndpoint() throws Exception {
		
		ResponseEntity<String> decodedBase64RightMessage = controller.getDecodedBase64RightMessage(99);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, decodedBase64RightMessage.getStatusCode());
	}
	
	@AfterAll
	public void tearDown() {
		repository = null;
	}

}
