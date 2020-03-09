package io.github.mariazevedo88.diffapi.it;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.mariazevedo88.diffapi.dto.MessageDTO;
import io.github.mariazevedo88.diffapi.enumeration.ResultDiffEnum;

/**
 * Class that implements API integration tests
 * 
 * @author Mariana Azevedo
 * @since 08/03/2020
 *
 */
@SpringBootTest
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, MockitoTestExecutionListener.class })
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("DiffApiApplicationIT")
@TestInstance(Lifecycle.PER_CLASS)
public class DiffApiApplicationIT {

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Autowired
    private MockMvc mockMvc;
	
	@BeforeAll
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
	        .build();
	}
	
	@Test
	@DisplayName("Returns all messages' list")
    public void shouldReturnEmptyListMessagesCreated() throws Exception {
        this.mockMvc.perform(get("/v1/diff/all")).andExpect(status().isOk());
    }
	
	@Test
	@DisplayName("Verify if JSON Base64 was created in the left endpoint")
	public void shouldCreateJSONBase64InLeftEndpoint() throws Exception {
		
		this.mockMvc.perform(post("/v1/diff/1/left")
				.content(getLeftJsonPayload("VGVzdGU="))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	@DisplayName("Verify if JSON Base64 was created in the right endpoint")
	public void shouldCreateJSONBase64InRightEndpoint() throws Exception {
		
		this.mockMvc.perform(post("/v1/diff/1/right")
				.content(getRightJsonPayload("VGVzdGU="))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	@DisplayName("Send invalid data to the left endpoint")
	public void shouldSendInvalidDataToLeftEndpoint() throws Exception {
		
		this.mockMvc.perform(post("/v1/diff/4/left")
				.content(getRightJsonPayload("Test"))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is5xxServerError())
				.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	@DisplayName("Send invalid data to the right endpoint")
	public void shouldSendInvalidDataToRightEndpoint() throws Exception {
		
		this.mockMvc.perform(post("/v1/diff/4/right")
				.content(getLeftJsonPayload("Test"))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().is5xxServerError())
				.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	@DisplayName("Checks if endpoint message comparison returns equal.")
	public void shouldTheComparisonResultBeEqual() throws Exception {
		
		this.mockMvc.perform(post("/v1/diff/20/left")
        		.content(getLeftJsonPayload("Test"))
        		.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isCreated());
		
		this.mockMvc.perform(post("/v1/diff/20/right")
        		.content(getRightJsonPayload("Test"))
        		.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isCreated());
		
		this.mockMvc.perform(get("/v1/diff/20"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.result").value(ResultDiffEnum.EQUAL.getValue()));
	}
	
	@Test
	@DisplayName("Checks if endpoint message comparison returns different by size.")
	public void shouldTheComparisonResultBeDifferentSize() throws Exception {
		
		this.mockMvc.perform(post("/v1/diff/30/left")
        		.content(getLeftJsonPayload("Anna"))
        		.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isCreated());
		
		this.mockMvc.perform(post("/v1/diff/30/right")
        		.content(getRightJsonPayload("Mariana"))
        		.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isCreated());
		
		this.mockMvc.perform(get("/v1/diff/30"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.result").value(ResultDiffEnum.DIFFERENT_SIZE.getValue()));
	}
	
	
	@Test
	@DisplayName("Checks if endpoint message comparison returns completly different strings.")
	public void shouldTheComparisonResultBeCompletlyDifferent() throws Exception {
		
        this.mockMvc.perform(post("/v1/diff/40/left")
        		.content(getLeftJsonPayload("QXBwbGU="))
        		.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isCreated());
		
		this.mockMvc.perform(post("/v1/diff/40/right")
        		.content(getRightJsonPayload("SnVpY2U="))
        		.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isCreated());
		
		this.mockMvc.perform(get("/v1/diff/40"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.result").value(ResultDiffEnum.DIFFERENT.getValue()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.diff.offset").value(0))
	        	.andExpect(MockMvcResultMatchers.jsonPath("$.data.diff.length").value(6));
	}
	
	public String getLeftJsonPayload(String leftData) throws JsonProcessingException {
		
		MessageDTO dto = new MessageDTO();
		dto.setLeftData(leftData);
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(dto);
	}
	
	public String getRightJsonPayload(String rightData) throws JsonProcessingException {
		
		MessageDTO dto = new MessageDTO();
		dto.setRightData(rightData);
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(dto);
	}

}
