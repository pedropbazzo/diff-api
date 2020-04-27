package io.github.mariazevedo88.diffapi.it;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URL;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.mariazevedo88.diffapi.dto.model.message.MessageDTO;
import io.github.mariazevedo88.diffapi.model.enumeration.ResultDiffEnum;

/**
 * Class that implements API integration tests
 * 
 * @author Mariana Azevedo
 * @since 08/03/2020
 */
@ActiveProfiles("test")
@DisplayName("DiffApiApplicationIT")
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DiffApiApplicationIT {

	@LocalServerPort
	private int port;
	 
    @Autowired
    private TestRestTemplate restTemplate;
	
    @Test
    @Order(1)
	@DisplayName("Verify if JSON Base64 was created in the left endpoint")
	public void shouldCreateJSONBase64InLeftEndpoint() throws Exception {
    	
    	RequestEntity<String> requestEntity = RequestEntity.post(
    		new URL("http://localhost:" + port + "/v1/diff/1/left").toURI())
    		.contentType(MediaType.APPLICATION_JSON).body(getLeftJsonPayload(1L, "VGVzdGU=")); 
    	ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
    	
        assertEquals(201, responseEntity.getStatusCodeValue());
	}
	
	@Test
	@Order(2)
	@DisplayName("Verify if JSON Base64 was created in the right endpoint")
	public void shouldCreateJSONBase64InRightEndpoint() throws Exception {
		
		RequestEntity<String> requestEntity = RequestEntity.post(
    		new URL("http://localhost:" + port + "/v1/diff/1/right").toURI())
    		.contentType(MediaType.APPLICATION_JSON).body(getRightJsonPayload(1L, "VGVzdGU=")); 
    	ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
	    	
	    assertEquals(200, responseEntity.getStatusCodeValue());
	}
	
	@Test
	@Order(3)
	@DisplayName("Checks if endpoint message comparison returns equal.")
	public void shouldTheComparisonResultBeEqual() throws Exception {
		
		ResponseEntity<String> responseEntity = this.restTemplate
		           .getForEntity("http://localhost:" + port + "/v1/diff/1", String.class);
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode root = objectMapper.readTree(responseEntity.getBody());
        
		assertEquals(200, responseEntity.getStatusCodeValue());
		assertEquals(ResultDiffEnum.EQUAL.getValue(), root.get("data").get("result").textValue());
	}

	@Test
    @Order(4)
	@DisplayName("Returns all diffs' list")
    public void shouldFindAllDiffs() {
    	
    	ResponseEntity<String> responseEntity = this.restTemplate
           .getForEntity("http://localhost:" + port + "/v1/diff/all", String.class);
                
        assertEquals(200, responseEntity.getStatusCodeValue());
    }
	
	@Test
	@Order(5)
	@DisplayName("Send null data to the left endpoint")
	public void shouldSendInvalidDataToLeftEndpoint() throws Exception {
		
		RequestEntity<String> requestEntity = RequestEntity.post(
	    		new URL("http://localhost:" + port + "/v1/diff/2/left").toURI())
	    		.contentType(MediaType.APPLICATION_JSON).body(getLeftJsonPayload(2L, null)); 
	    ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
	            
	    assertEquals(500, responseEntity.getStatusCodeValue());
	}
	
	@Test
	@Order(6)
	@DisplayName("Send null data to the right endpoint")
	public void shouldSendInvalidDataToRightEndpoint() throws Exception {
		
		RequestEntity<String> requestEntity = RequestEntity.post(
	    		new URL("http://localhost:" + port + "/v1/diff/2/right").toURI())
	    		.contentType(MediaType.APPLICATION_JSON).body(getRightJsonPayload(2L, null)); 
	    ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
	            
	    assertEquals(500, responseEntity.getStatusCodeValue());
	}
	
	@Test
	@Order(7)
	@DisplayName("Create JSON Base64 with id=2 in the left endpoint")
	public void shouldCreateNewDataToLeftEndpoint() throws Exception {
		
		RequestEntity<String> requestEntity = RequestEntity.post(
	    		new URL("http://localhost:" + port + "/v1/diff/2/left").toURI())
	    		.contentType(MediaType.APPLICATION_JSON).body(getLeftJsonPayload(2L, "QmFuYW5h")); 
	    ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
	            
	    assertEquals(201, responseEntity.getStatusCodeValue());
	}
	
	@Test
	@Order(8)
	@DisplayName("Create JSON Base64 with id=2 in the right endpoint")
	public void shouldCreateNewDataToRightEndpoint() throws Exception {
		
		RequestEntity<String> requestEntity = RequestEntity.post(
	    		new URL("http://localhost:" + port + "/v1/diff/2/right").toURI())
	    		.contentType(MediaType.APPLICATION_JSON).body(getRightJsonPayload(2L, "TWHDsWFuYQ==")); 
	    ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
	            
	    assertEquals(200, responseEntity.getStatusCodeValue());
	}
	
	@Test
	@Order(9)
	@DisplayName("Checks if endpoint message comparison returns different by size.")
	public void shouldTheComparisonResultBeDifferentSize() throws Exception {
		
		ResponseEntity<String> responseEntity = this.restTemplate
		           .getForEntity("http://localhost:" + port + "/v1/diff/2", String.class);
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode root = objectMapper.readTree(responseEntity.getBody());
     
		assertEquals(200, responseEntity.getStatusCodeValue());
		assertEquals(ResultDiffEnum.DIFFERENT_SIZE.getValue(), root.get("data").get("result").textValue());
	}
	
	@Test
	@Order(10)
	@DisplayName("Create JSON Base64 with id=3 in the left endpoint")
	public void shouldCreateAnotherDataToLeftEndpoint() throws Exception {
		
		RequestEntity<String> requestEntity = RequestEntity.post(
	    		new URL("http://localhost:" + port + "/v1/diff/3/left").toURI())
	    		.contentType(MediaType.APPLICATION_JSON).body(getLeftJsonPayload(3L, "QmlrZQ==")); 
	    ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
	            
	    assertEquals(201, responseEntity.getStatusCodeValue());
	}
	
	@Test
	@Order(11)
	@DisplayName("Create JSON Base64 with id=3 in the right endpoint")
	public void shouldCreateAnotherDataToRightEndpoint() throws Exception {
		
		RequestEntity<String> requestEntity = RequestEntity.post(
	    		new URL("http://localhost:" + port + "/v1/diff/3/right").toURI())
	    		.contentType(MediaType.APPLICATION_JSON).body(getRightJsonPayload(3L, "S2l0ZQ==")); 
	    ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
	            
	    assertEquals(200, responseEntity.getStatusCodeValue());
	}
	
	@Test
	@Order(12)
	@DisplayName("Checks if endpoint message comparison returns completely different strings.")
	public void shouldTheComparisonResultBeCompletelyDifferent() throws Exception {
		
		ResponseEntity<String> responseEntity = this.restTemplate
		           .getForEntity("http://localhost:" + port + "/v1/diff/3", String.class);
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode root = objectMapper.readTree(responseEntity.getBody());
     
		assertEquals(200, responseEntity.getStatusCodeValue());
		assertEquals(ResultDiffEnum.DIFFERENT.getValue(), root.get("data").get("result").textValue());
	}
	
	public String getLeftJsonPayload(Long id, String leftData) throws JsonProcessingException {
		
		MessageDTO dto = new MessageDTO();
		dto.setId(id);
		dto.setLeftData(leftData);
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(dto);
	}
	
	public String getRightJsonPayload(Long id, String rightData) throws JsonProcessingException {
		
		MessageDTO dto = new MessageDTO();
		dto.setId(id);
		dto.setRightData(rightData);
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(dto);
	}

}
