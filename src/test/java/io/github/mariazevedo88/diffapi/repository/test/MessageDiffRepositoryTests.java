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

import io.github.mariazevedo88.diffapi.model.MessageDiff;
import io.github.mariazevedo88.diffapi.repository.MessageDiffRepository;

@SpringBootTest
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class MessageDiffRepositoryTests {
	
	@Autowired
	MessageDiffRepository repository;
	
	@BeforeAll
	private void setUp() {
		
		MessageDiff message = new MessageDiff();
		message.setLength(0);
		message.setLength(9);
		
		repository.save(message);
	}
	
	@AfterAll
	private void tearDown() {
		repository.deleteAll();
	}
	
	@Test
	public void testSave() {
		
		MessageDiff message = new MessageDiff();
		message.setLength(1);
		message.setOffset(16);
		
		MessageDiff response = repository.save(message);
		
		assertNotNull(response);
	}
	
	@Test
	public void testFindById(){
		Optional<MessageDiff> response = repository.findById(2L);
		
		assertTrue(response.isPresent());
		assertEquals(response.get().getId(), 2L);
	}

}
