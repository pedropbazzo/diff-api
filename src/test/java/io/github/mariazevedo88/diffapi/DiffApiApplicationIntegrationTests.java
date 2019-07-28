package io.github.mariazevedo88.diffapi;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import io.github.mariazevedo88.diffapi.controller.ApiController;
import io.github.mariazevedo88.diffapi.enumeration.ResultDiffEnum;
import io.github.mariazevedo88.diffapi.model.JSONMessage;
import io.github.mariazevedo88.diffapi.model.MessageDiff;
import io.github.mariazevedo88.diffapi.model.ResultDiff;

/**
 * Class that implements API integration tests
 * 
 * @author Mariana Azevedo
 * @since 27/07/2019
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("DiffApiApplicationIntegrationTests")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class DiffApiApplicationIntegrationTests {

	@Autowired
    private ApiController controller;
	
	@Autowired
    private MockMvc mockMvc;
	
	@Test
	@DisplayName("Verify if has messages")
	@Order(1)
    public void shouldReturnEmptyListOfMessages() throws Exception {
        this.mockMvc.perform(get("/")).andExpect(status().isNotFound());
    }

	@Test
	@DisplayName("Verify if JSON Base64 was created in the left endpoint")
	@Order(2)
	public void shouldCreateJSONBase64InLeftEndpoint() throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("message", "SGVsbG8gV29ybGQ=");
		ResponseEntity<JSONMessage> saveLeftMessage = controller.createLeftJSONMessage(1L, new JSONObject(map));
		
		assertEquals(HttpStatus.CREATED, saveLeftMessage.getStatusCode());
	}
	
	@Test
	@DisplayName("Verify if JSON Base64 was created in the right endpoint")
	@Order(3)
	public void shouldCreateJSONBase64InRightEndpoint() throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("message", "SGVsbG8gV29ybGQ=");
		ResponseEntity<JSONMessage> saveRightMessage = controller.createRightJSONMessage(1L, new JSONObject(map));
		
		assertEquals(HttpStatus.CREATED, saveRightMessage.getStatusCode());
	}
	
	@Test
	@DisplayName("Checks if endpoint message comparison returns equal.")
	@Order(4)
	public void shouldTheComparisonResultBeEqual() throws Exception {
		
		long id = 1L;
		
		ResponseEntity<ResultDiff> result = controller.compare(id);
		assertEquals(HttpStatus.OK, result.getStatusCode());
		
		assertEquals(ResultDiffEnum.EQUAL, result.getBody().getResult());
		assertNull(result.getBody().getDiffs());
	}
	
	@Test
	@DisplayName("Checks if endpoint message comparison returns different by size.")
	@Order(5)
	public void shouldTheComparisonResultBeDifferentSize() throws Exception {
		
		long id = 4L;
		
		Map<String, String> mapLeft = new HashMap<>();
		mapLeft.put("message", "TWFyaWFuYQ==");
		ResponseEntity<JSONMessage> saveLeftMessage = controller.createLeftJSONMessage(id, new JSONObject(mapLeft));
		
		assertEquals(HttpStatus.CREATED, saveLeftMessage.getStatusCode());
		
		Map<String, String> mapRight = new HashMap<>();
		mapRight.put("message", "THVjYXM=");
		ResponseEntity<JSONMessage> saveRightMessage = controller.createRightJSONMessage(id, new JSONObject(mapRight));
		
		assertEquals(HttpStatus.CREATED, saveRightMessage.getStatusCode());
		
		controller.createLeftJSONMessage(id, new JSONObject(mapLeft));
		controller.createRightJSONMessage(id, new JSONObject(mapRight));
		
		ResponseEntity<ResultDiff> result = controller.compare(id);
		assertEquals(HttpStatus.OK, result.getStatusCode());
		
		assertEquals(ResultDiffEnum.DIFFERENT_SIZE, result.getBody().getResult());
		assertNull(result.getBody().getDiffs());
		
	}
	
	@Test
	@DisplayName("Checks if endpoint message comparison returns different in the end of the string.")
	@Order(6)
	public void shouldTheComparisonResultBeDifferentInEndOfString() throws Exception {
		
		long id = 5L;
		
		Map<String, String> mapLeft = new HashMap<>();
		mapLeft.put("message", "TWFyaWFuYQ==");
		ResponseEntity<JSONMessage> saveLeftMessage = controller.createLeftJSONMessage(id, new JSONObject(mapLeft));
		
		assertEquals(HttpStatus.CREATED, saveLeftMessage.getStatusCode());
		
		Map<String, String> mapRight = new HashMap<>();
		mapRight.put("message", "TWFyaWFuZQ==");
		ResponseEntity<JSONMessage> saveRightMessage = controller.createRightJSONMessage(id, new JSONObject(mapRight));
		
		assertEquals(HttpStatus.CREATED, saveRightMessage.getStatusCode());
		
		controller.createLeftJSONMessage(id, new JSONObject(mapLeft));
		controller.createRightJSONMessage(id, new JSONObject(mapRight));
		
		Object[] diffList = {new MessageDiff(8, 1)};

		ResponseEntity<ResultDiff> result = controller.compare(id);
		assertEquals(HttpStatus.OK, result.getStatusCode());
		
		assertEquals(ResultDiffEnum.DIFFERENT, result.getBody().getResult());
		assertNotNull(result.getBody().getDiffs());
		assertArrayEquals(diffList, result.getBody().getDiffs().toArray());
		
	}
	
	@Test
	@DisplayName("Checks if endpoint message comparison returns different in the beginning of the string.")
	@Order(7)
	public void shouldTheComparisonResultBeDifferentInBeginningOfString() throws Exception {
		
		long id = 6L;
		
		Map<String, String> mapLeft = new HashMap<>();
		mapLeft.put("message", "TWFyaWFuYQ==");
		ResponseEntity<JSONMessage> saveLeftMessage = controller.createLeftJSONMessage(id, new JSONObject(mapLeft));
		
		assertEquals(HttpStatus.CREATED, saveLeftMessage.getStatusCode());
		
		Map<String, String> mapRight = new HashMap<>();
		mapRight.put("message", "SnVsaWFuYQ==");
		ResponseEntity<JSONMessage> saveRightMessage = controller.createRightJSONMessage(id, new JSONObject(mapRight));
		
		assertEquals(HttpStatus.CREATED, saveRightMessage.getStatusCode());
		
		controller.createLeftJSONMessage(id, new JSONObject(mapLeft));
		controller.createRightJSONMessage(id, new JSONObject(mapRight));
		
		Object[] diffList = {new MessageDiff(0, 4)};

		ResponseEntity<ResultDiff> result = controller.compare(id);
		assertEquals(HttpStatus.OK, result.getStatusCode());
		
		assertEquals(ResultDiffEnum.DIFFERENT, result.getBody().getResult());
		assertNotNull(result.getBody().getDiffs());
		assertArrayEquals(diffList, result.getBody().getDiffs().toArray());
		
	}
	
	@Test
	@DisplayName("Checks if endpoint message comparison returns completly different strings.")
	@Order(8)
	public void shouldTheComparisonResultBeCompletlyDifferent() throws Exception {
		
		long id = 7L;
		
		Map<String, String> mapLeft = new HashMap<>();
		mapLeft.put("message", "TWHDp8Oj");
		ResponseEntity<JSONMessage> saveLeftMessage = controller.createLeftJSONMessage(id, new JSONObject(mapLeft));
		
		assertEquals(HttpStatus.CREATED, saveLeftMessage.getStatusCode());
		
		Map<String, String> mapRight = new HashMap<>();
		mapRight.put("message", "UMOqcmE=");
		ResponseEntity<JSONMessage> saveRightMessage = controller.createRightJSONMessage(id, new JSONObject(mapRight));
		
		assertEquals(HttpStatus.CREATED, saveRightMessage.getStatusCode());
		
		controller.createLeftJSONMessage(id, new JSONObject(mapLeft));
		controller.createRightJSONMessage(id, new JSONObject(mapRight));
		
		Object[] diffList = {new MessageDiff(0, 8)};

		ResponseEntity<ResultDiff> result = controller.compare(id);
		assertEquals(HttpStatus.OK, result.getStatusCode());
		
		assertEquals(ResultDiffEnum.DIFFERENT, result.getBody().getResult());
		assertNotNull(result.getBody().getDiffs());
		assertArrayEquals(diffList, result.getBody().getDiffs().toArray());
	}
	
	@Test
	@DisplayName("List all messages created")
	@Order(9)
    public void shouldReturnAllMessagesCreated() throws Exception {
        this.mockMvc.perform(get("/")).andExpect(status().isOk());
    }

}
