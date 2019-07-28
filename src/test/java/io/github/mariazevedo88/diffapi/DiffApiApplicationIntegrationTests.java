package io.github.mariazevedo88.diffapi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.mariazevedo88.diffapi.enumeration.ResultDiffEnum;

/**
 * Class that implements API integration tests
 * 
 * @author Mariana Azevedo
 * @since 27/07/2019
 *
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("DiffApiApplicationIntegrationTests")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class DiffApiApplicationIntegrationTests {

	@Autowired
    private MockMvc mockMvc;
	
	@Test
	@DisplayName("Returns an empty messages' list")
	@Order(1)
    public void shouldReturnEmptyListMessagesCreated() throws Exception {
		this.mockMvc.perform(get("/v1/diff/cleanAllMessages")).andExpect(status().isOk());
        this.mockMvc.perform(get("/")).andExpect(status().isNotFound());
        this.mockMvc.perform(get("/v1/diff/left/all")).andExpect(status().isNotFound());
        this.mockMvc.perform(get("/v1/diff/right/all")).andExpect(status().isNotFound());
    }
	
	@Test
	@DisplayName("Verify if JSON Base64 was created in the left endpoint")
	@Order(2)
	public void shouldCreateJSONBase64InLeftEndpoint() throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("message", "SGVsbG8gV29ybGQ=");
		
		this.mockMvc.perform(post("/v1/diff/1/left").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        		.content(new ObjectMapper().writeValueAsString(map))).andExpect(status().isCreated());
	}
	
	@Test
	@DisplayName("Verify if JSON Base64 was created in the right endpoint")
	@Order(3)
	public void shouldCreateJSONBase64InRightEndpoint() throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("message", "SGVsbG8gV29ybGQ=");
		
		this.mockMvc.perform(post("/v1/diff/1/right").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        		.content(new ObjectMapper().writeValueAsString(map))).andExpect(status().isCreated());
	}
	
	@Test
	@DisplayName("Checks if endpoint message comparison returns equal.")
	@Order(4)
	public void shouldTheComparisonResultBeEqual() throws Exception {
		
		Map<String, String> map = new HashMap<>();
		map.put("message", "SGVsbG8gV29ybGQ=");
		
		this.mockMvc.perform(post("/v1/diff/1/left").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        		.content(new ObjectMapper().writeValueAsString(map))).andExpect(status().isCreated());
		
		this.mockMvc.perform(post("/v1/diff/1/right").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        		.content(new ObjectMapper().writeValueAsString(map))).andExpect(status().isCreated());
		
		this.mockMvc.perform(get("/v1/diff/1")).andExpect(status().isOk())
    	.andExpect(MockMvcResultMatchers.jsonPath("$.result").value(ResultDiffEnum.EQUAL.getValue()));
	}
	
	@Test
	@DisplayName("Checks if endpoint message comparison returns different by size.")
	@Order(5)
	public void shouldTheComparisonResultBeDifferentSize() throws Exception {
		
		Map<String, String> mapLeft = new HashMap<>();
		mapLeft.put("message", "TWFyaWFuYQ==");
		
		Map<String, String> mapRight = new HashMap<>();
		mapRight.put("message", "THVjYXM=");

		this.mockMvc.perform(post("/v1/diff/4/left").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        		.content(new ObjectMapper().writeValueAsString(mapLeft))).andExpect(status().isCreated());
		
		this.mockMvc.perform(post("/v1/diff/4/right").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        		.content(new ObjectMapper().writeValueAsString(mapRight))).andExpect(status().isCreated());
		
        this.mockMvc.perform(get("/v1/diff/4")).andExpect(status().isOk())
        	.andExpect(MockMvcResultMatchers.jsonPath("$.result").value(ResultDiffEnum.DIFFERENT_SIZE.getValue()));
	}
	
	@Test
	@DisplayName("Checks if endpoint message comparison returns different in the end of the string.")
	@Order(6)
	public void shouldTheComparisonResultBeDifferentInEndOfString() throws Exception {
		
		Map<String, String> mapLeft = new HashMap<>();
		mapLeft.put("message", "TWFyaWFuYQ==");
		
		Map<String, String> mapRight = new HashMap<>();
		mapRight.put("message", "TWFyaWFuZQ==");

		this.mockMvc.perform(post("/v1/diff/5/left").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        		.content(new ObjectMapper().writeValueAsString(mapLeft))).andExpect(status().isCreated());
		
		this.mockMvc.perform(post("/v1/diff/5/right").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        		.content(new ObjectMapper().writeValueAsString(mapRight))).andExpect(status().isCreated());
		
        this.mockMvc.perform(get("/v1/diff/5")).andExpect(status().isOk())
        	.andExpect(MockMvcResultMatchers.jsonPath("$.result").value(ResultDiffEnum.DIFFERENT.getValue()))
        	.andExpect(MockMvcResultMatchers.jsonPath("$.diffs[0].offset").value("8"))
        	.andExpect(MockMvcResultMatchers.jsonPath("$.diffs[0].length").value("1"));
	}
	
	@Test
	@DisplayName("Checks if endpoint message comparison returns different in the beginning of the string.")
	@Order(7)
	public void shouldTheComparisonResultBeDifferentInBeginningOfString() throws Exception {
		
		Map<String, String> mapLeft = new HashMap<>();
		mapLeft.put("message", "TWFyaWFuYQ==");
		
		Map<String, String> mapRight = new HashMap<>();
		mapRight.put("message", "SnVsaWFuYQ==");

		this.mockMvc.perform(post("/v1/diff/6/left").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        		.content(new ObjectMapper().writeValueAsString(mapLeft))).andExpect(status().isCreated());
		
		this.mockMvc.perform(post("/v1/diff/6/right").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        		.content(new ObjectMapper().writeValueAsString(mapRight))).andExpect(status().isCreated());
		
        this.mockMvc.perform(get("/v1/diff/6")).andExpect(status().isOk())
        	.andExpect(MockMvcResultMatchers.jsonPath("$.result").value(ResultDiffEnum.DIFFERENT.getValue()))
        	.andExpect(MockMvcResultMatchers.jsonPath("$.diffs[0].offset").value("0"))
        	.andExpect(MockMvcResultMatchers.jsonPath("$.diffs[0].length").value("4"));
	}
	
	@Test
	@DisplayName("Checks if endpoint message comparison returns completly different strings.")
	@Order(8)
	public void shouldTheComparisonResultBeCompletlyDifferent() throws Exception {
		
		Map<String, String> mapLeft = new HashMap<>();
		mapLeft.put("message", "QXBwbGU=");
		
		Map<String, String> mapRight = new HashMap<>();
		mapRight.put("message", "SnVpY2U=");

		this.mockMvc.perform(post("/v1/diff/7/left").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        		.content(new ObjectMapper().writeValueAsString(mapLeft))).andExpect(status().isCreated());
		
		this.mockMvc.perform(post("/v1/diff/7/right").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        		.content(new ObjectMapper().writeValueAsString(mapRight))).andExpect(status().isCreated());
		
        this.mockMvc.perform(get("/v1/diff/7")).andExpect(status().isOk())
        	.andExpect(MockMvcResultMatchers.jsonPath("$.result").value(ResultDiffEnum.DIFFERENT.getValue()))
        	.andExpect(MockMvcResultMatchers.jsonPath("$.diffs[0].offset").value("0"))
        	.andExpect(MockMvcResultMatchers.jsonPath("$.diffs[0].length").value("6"));
	}
	
	@Test
	@DisplayName("List all messages created")
	@Order(9)
    public void shouldReturnAllMessagesCreated() throws Exception {
		
		Map<String, String> mapLeft = new HashMap<>();
		mapLeft.put("message", "QXBwbGU=");
		
		Map<String, String> mapRight = new HashMap<>();
		mapRight.put("message", "SnVpY2U=");

		this.mockMvc.perform(post("/v1/diff/66/left").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        		.content(new ObjectMapper().writeValueAsString(mapLeft))).andExpect(status().isCreated());
		
		this.mockMvc.perform(post("/v1/diff/66/right").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        		.content(new ObjectMapper().writeValueAsString(mapRight))).andExpect(status().isCreated());
		
        this.mockMvc.perform(get("/")).andExpect(status().isOk());
    }
	
	@Test
	@DisplayName("List all left messages created")
	@Order(10)
    public void shouldReturnAllLeftMessagesCreated() throws Exception {
        this.mockMvc.perform(get("/v1/diff/left/all")).andExpect(status().isOk());
    }
	
	@Test
	@DisplayName("List all right messages created")
	@Order(11)
    public void shouldReturnAllRightMessagesCreated() throws Exception {
        this.mockMvc.perform(get("/v1/diff/right/all")).andExpect(status().isOk());
    }
	
	@Test
	@DisplayName("Decode a message in the left endpoint")
	@Order(12)
    public void shouldReturnADecodedMessageFromLeftEndpoint() throws Exception {
		
		Map<String, String> map = new HashMap<>();
		map.put("message", "TWFyaWFuYQ==");
		this.mockMvc.perform(post("/v1/diff/88/left").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        		.content(new ObjectMapper().writeValueAsString(map))).andExpect(status().isCreated());
		
        this.mockMvc.perform(get("/v1/diff/88/left/decodeString")).andExpect(status().isOk());
    }
	
	@Test
	@DisplayName("Decode a message in the right endpoint")
	@Order(13)
    public void shouldReturnADecodedMessageFromRightEndpoint() throws Exception {
		
		Map<String, String> map = new HashMap<>();
		map.put("message", "SnVpY2U=");
		this.mockMvc.perform(post("/v1/diff/89/right").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        		.content(new ObjectMapper().writeValueAsString(map))).andExpect(status().isCreated());
		
        this.mockMvc.perform(get("/v1/diff/89/right/decodeString")).andExpect(status().isOk());
    }
	
	@Test
	@DisplayName("Checks if endpoint message comparison returns null, because the message on right endpoint is null.")
	@Order(14)
    public void shouldReturnErrorOnLeftEndpoint() throws Exception {
		
		Map<String, String> mapLeft = new HashMap<>();
		mapLeft.put("message", "SGVsbG8gV29ybGQ=");
		
        this.mockMvc.perform(post("/v1/diff/34/left").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        		.content(new ObjectMapper().writeValueAsString(mapLeft))).andExpect(status().isCreated());
        
        this.mockMvc.perform(get("/v1/diff/34")).andExpect(status().is5xxServerError());
    }
	
	@Test
	@DisplayName("Checks if endpoint message comparison returns null, because the message on left endpoint is null.")
	@Order(15)
    public void shouldReturnErrorOnRightEndpoint() throws Exception {
		
		Map<String, String> mapRight = new HashMap<>();
		mapRight.put("message", "SGVsbG8gV29ybGQ=");
		
		this.mockMvc.perform(post("/v1/diff/35/right").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        		.content(new ObjectMapper().writeValueAsString(mapRight))).andExpect(status().isCreated());
        
        this.mockMvc.perform(get("/v1/diff/35")).andExpect(status().is5xxServerError());
    }
	
	@Test
	@DisplayName("Checks if endpoint message comparison returns null, because the message on left endpoint is null.")
	@Order(16)
    public void shouldReturnErrorOnBothEndpoints() throws Exception {
		this.mockMvc.perform(get("/v1/diff/50")).andExpect(status().is5xxServerError());
    }
	
	@Test
	@DisplayName("Encode a message in the left endpoint")
	@Order(17)
    public void shouldReturnAnEncodedMessageFromLeftEndpoint() throws Exception {
		
		Map<String, String> mapLeft = new HashMap<>();
		mapLeft.put("message", "Hello World");
		
        this.mockMvc.perform(post("/v1/diff/22/left/encodeString").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        		.content(new ObjectMapper().writeValueAsString(mapLeft))).andExpect(status().isOk());
    }
	
	@Test
	@DisplayName("Encode a message in the right endpoint")
	@Order(18)
    public void shouldReturnAnEncodedMessageFromRightEndpoint() throws Exception {
		
		Map<String, String> mapRight = new HashMap<>();
		mapRight.put("message", "Hello World!");
		
		this.mockMvc.perform(post("/v1/diff/22/right/encodeString").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        		.content(new ObjectMapper().writeValueAsString(mapRight))).andExpect(status().isOk());
    }
	
	@Test
	@DisplayName("Checks if API respond error on create a JSON Base64 from a null String in left endpoint")
	@Order(19)
    public void shouldReturnErrorOnCreateJSONBase64InLeftEndpoint() throws Exception {
		
		Map<String, String> mapLeft = new HashMap<>();
		mapLeft.put("message", null);
		
        this.mockMvc.perform(post("/v1/diff/28/left").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        		.content(new ObjectMapper().writeValueAsString(mapLeft))).andExpect(status().is5xxServerError());
    }
	
	@Test
	@DisplayName("Checks if API respond error on create a JSON Base64 from a null String in right endpoint")
	@Order(20)
    public void shouldReturnErrorOnCreateJSONBase64InRightEndpoint() throws Exception {
		
		Map<String, String> mapRight = new HashMap<>();
		mapRight.put("message", null);
		
		this.mockMvc.perform(post("/v1/diff/28/right").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        		.content(new ObjectMapper().writeValueAsString(mapRight))).andExpect(status().is5xxServerError());
    }
	
	@Test
	@DisplayName("Checks if API respond error on encode a JSON Base64 from a null String in left endpoint")
	@Order(21)
    public void shouldReturnErrorOnEncodeStringInLeftEndpoint() throws Exception {
		
		Map<String, String> mapLeft = new HashMap<>();
		mapLeft.put("message", null);
		
        this.mockMvc.perform(post("/v1/diff/32/left/encodeString").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        		.content(new ObjectMapper().writeValueAsString(mapLeft))).andExpect(status().is5xxServerError());
    }
	
	@Test
	@DisplayName("Checks if API respond error on encode a JSON Base64 from a null String in right endpoint")
	@Order(22)
    public void shouldReturnErrorOnEncodeStringInRightEndpoint() throws Exception {
		
		Map<String, String> mapRight = new HashMap<>();
		mapRight.put("message", null);
		
		this.mockMvc.perform(post("/v1/diff/32/right/encodeString").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        		.content(new ObjectMapper().writeValueAsString(mapRight))).andExpect(status().is5xxServerError());
    }

}
