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

import io.github.mariazevedo88.diffapi.model.MessageDiff;
import io.github.mariazevedo88.diffapi.repository.diff.MessageDiffRepository;
import io.github.mariazevedo88.diffapi.service.MessageDiffService;

/**
 * Class that implements tests for MessageDiffService
 *  
 * @author Mariana Azevedo
 * @since 08/03/2020
 */
@SpringBootTest
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, MockitoTestExecutionListener.class })
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class MessageDiffServiceTests {
	
	@MockBean
	MessageDiffRepository repository;
	
	@Autowired
	MessageDiffService service;
	
	@BeforeAll
	public void setUp() {
		BDDMockito.given(repository.findById(Mockito.anyLong()))
			.willReturn(Optional.of(new MessageDiff()));
	}
	
	@Test
	public void testFindById() {
		Optional<MessageDiff> message = service.findById(1L);
		assertTrue(message.isPresent());
	}

}
