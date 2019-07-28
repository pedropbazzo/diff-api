package io.github.mariazevedo88.diffapi;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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
import org.springframework.http.ResponseEntity;

import io.github.mariazevedo88.diffapi.controller.ApiController;
import io.github.mariazevedo88.diffapi.enumeration.ResultDiffEnum;
import io.github.mariazevedo88.diffapi.model.JSONMessage;
import io.github.mariazevedo88.diffapi.model.MessageDiff;
import io.github.mariazevedo88.diffapi.model.ResultDiff;
import io.github.mariazevedo88.diffapi.repository.JSONMessageRepository;
import io.github.mariazevedo88.diffapi.service.ComparatorService;

/**
 * Class that implements API unit tests
 * 
 * @author Mariana Azevedo
 * @since 27/07/2019
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("DiffApiApplicationUnitTests")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class DiffApiApplicationUnitTests {
	
	@Autowired
    private ApiController controller;
	
	@Autowired
	private ComparatorService comparatorService;
	
	private JSONMessageRepository repository;
	
	@BeforeAll
	public void setUp() {
		repository = (JSONMessageRepository) JSONMessageRepository.getInstance();
	}
	
	@Test
	@DisplayName("Verify if controller exists on context loads")
	@Order(1)
	public void shouldReturnNotNullController() throws Exception {
		assertNotNull(controller);
	}
	
	@Test
	@DisplayName("Verify if repository was created")
	@Order(2)
	public void shouldReturnNotNullRepository() {
		assertNotNull(repository);
	}
	
	@Test
	@DisplayName("Verify if JSON Base64 was created in the left endpoint")
	@Order(3)
	public void shouldCreateJSONBase64InLeftEndpoint() throws Exception {
		
		long id = 1;
		Map<String, String> map = new HashMap<>();
		map.put("message", "SGVsbG8gV29ybGQ=");
		
		ResponseEntity<JSONMessage> saveLeftMessage = controller.createLeftJSONMessage(id, new JSONObject(map));
		
		assertEquals(id, saveLeftMessage.getBody().getId());
		assertEquals(map.get("message"), saveLeftMessage.getBody().getValue());
	}
	
	@Test
	@DisplayName("Verify if JSON Base64 was created in the right endpoint")
	@Order(4)
	public void shouldCreateJSONBase64InRightEndpoint() throws Exception {
		
		long id = 1;
		Map<String, String> map = new HashMap<>();
		map.put("message", "SGVsbG8gV29ybGQ=");
		
		ResponseEntity<JSONMessage> saveRightMessage = controller.createRightJSONMessage(id, new JSONObject(map));
		
		assertEquals(id, saveRightMessage.getBody().getId());
		assertEquals(map.get("message"), saveRightMessage.getBody().getValue());
	}
	
	@Test
	@DisplayName("Checks whether a message in the left endpoint with the same id will have its values updated.")
	@Order(5)
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
	@Order(6)
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
	@Order(7)
	public void shouldTheComparisonResultBeEqual() throws Exception {
		
		long id = 3L;
		
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
	@Order(8)
	public void shouldTheComparisonResultBeDifferentSize() throws Exception {
		
		long id = 4L;
		
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
	@Order(9)
	public void shouldTheComparisonResultBeDifferentInEndOfString() throws Exception {
		
		long id = 5L;
		
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
	@Order(10)
	public void shouldTheComparisonResultBeDifferentInBeginningOfString() throws Exception {
		
		long id = 6L;
		
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
	@Order(11)
	public void shouldTheComparisonResultBeCompletlyDifferent() throws Exception {
		
		long id = 7L;
		
		Map<String, String> leftMessage = new HashMap<>();
		leftMessage.put("message", "TWHDp8Oj");
		
		Map<String, String> rightMessage = new HashMap<>();
		rightMessage.put("message", "UMOqcmE=");
		
		controller.createLeftJSONMessage(id, new JSONObject(leftMessage));
		controller.createRightJSONMessage(id, new JSONObject(rightMessage));
		
		Object[] diffList = {new MessageDiff(0, 8)};
		
		ResultDiff result = comparatorService.compare(id);
		assertNotNull(result);
		assertEquals(ResultDiffEnum.DIFFERENT, result.getResult());
		assertNotNull(result.getDiffs());
		assertArrayEquals(diffList, result.getDiffs().toArray());
	}
	
	@AfterAll
	public void tearDown() {
		repository = null;
	}

}
