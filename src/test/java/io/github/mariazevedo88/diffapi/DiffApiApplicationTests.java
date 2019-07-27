package io.github.mariazevedo88.diffapi;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.mariazevedo88.diffapi.repository.JSONMessageRepository;

@SpringBootTest
@DisplayName("DiffApiApplicationTests")
public class DiffApiApplicationTests {
	
	private JSONMessageRepository repository;

	@Test
	@Order(1)
	public void contextLoads() {
		repository = (JSONMessageRepository) JSONMessageRepository.getInstance();
	}
	
	@Test
	@Order(2)
	public void verifyIfRepositoryWasCreated() {
		assertNotNull(repository);
	}

}
