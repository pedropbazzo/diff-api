package io.github.mariazevedo88.diffapi.service.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import io.github.mariazevedo88.diffapi.model.Message;
import io.github.mariazevedo88.diffapi.repository.MessageRepository;
import io.github.mariazevedo88.diffapi.service.MessageService;

@SpringBootTest
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, MockitoTestExecutionListener.class })
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class MessageServiceTests {
	
	@MockBean
	MessageRepository repository;
	
	@Autowired
	MessageService service;
	
	@BeforeAll
	public void setUp() {
		BDDMockito.given(repository.findById(Mockito.anyLong()))
			.willReturn(Optional.of(new Message()));
	}
	
	@Test
	public void testFindById() {
		Optional<Message> message = service.findById(1L);
		assertTrue(message.isPresent());
	}
}
