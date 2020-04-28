package io.github.mariazevedo88.diffapi.repository.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import io.github.mariazevedo88.diffapi.model.message.Message;
import io.github.mariazevedo88.diffapi.repository.message.MessageRepository;

@SpringBootTest
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class MessageRepositoryTest {
	
	@Autowired
	MessageRepository repository;
	
	@BeforeAll
	private void setUp() {
		
		Message message = new Message();
		message.setLeftData("Test");
		
		repository.save(message);
	}
	
	@AfterAll
	private void tearDown() {
		repository.deleteAll();
	}
	
	@Test
	public void testSave() {
		
		Message message = new Message();
		message.setRightData("Test");
		
		Message response = repository.save(message);
		assertNotNull(response);
	}
	
	@Test
	public void testFindById(){
		
		Optional<Message> response = repository.findById(1L);
		
		assertTrue(response.isPresent());
		assertEquals(1L, response.get().getId());
	}

}
