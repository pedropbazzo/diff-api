package io.github.mariazevedo88.diffapi.controller.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.mariazevedo88.diffapi.dto.MessageDTO;
import io.github.mariazevedo88.diffapi.model.Message;
import io.github.mariazevedo88.diffapi.service.MessageService;

/**
 * Spring test class for DiffController
 * 
 * @author Mariana Azevedo
 * @since 08/03/2020
 */
@SpringBootTest
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, MockitoTestExecutionListener.class })
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class DiffControllerTests {
	
	private static final Long ID_LEFT = 1L;
	private static final Long ID_RIGHT = 2L;
	private static final String LEFT_DATA = "SGVsbG8gV29ybGQ=";
	private static final String RIGHT_DATA = "SGVsbG8gV29ybGQ=";

	@MockBean
	private MessageService service;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testSaveLeftEndpoint() throws Exception {
		
		BDDMockito.given(service.save(Mockito.any(Message.class))).willReturn(getMockLeftMessage());
		
		mockMvc.perform(post("/v1/diff/1/left")
				.content(getLeftJsonPayload(LEFT_DATA))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void testSaveRightEndpoint() throws Exception {
		
		BDDMockito.given(service.save(Mockito.any(Message.class))).willReturn(getMockRightMessage());
		
		mockMvc.perform(post("/v1/diff/2/right")
				.content(getRightJsonPayload(RIGHT_DATA))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andDo(MockMvcResultHandlers.print());
	}
	
	public Message getMockLeftMessage() {
		
		Message message = new Message();
		message.setId(ID_LEFT);
		message.setLeftData(LEFT_DATA);
		message.setRightData(null);
		
		return message;
	}
	
	public Message getMockRightMessage() {
		
		Message message = new Message();
		message.setId(ID_RIGHT);
		message.setRightData(RIGHT_DATA);
		message.setLeftData(null);
		
		return message;
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
