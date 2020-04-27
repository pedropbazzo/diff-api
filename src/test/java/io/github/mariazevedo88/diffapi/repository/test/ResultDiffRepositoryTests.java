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

import io.github.mariazevedo88.diffapi.model.diff.ResultDiff;
import io.github.mariazevedo88.diffapi.model.enumeration.ResultDiffEnum;
import io.github.mariazevedo88.diffapi.repository.diff.ResultDiffRepository;

@SpringBootTest
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class })
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class ResultDiffRepositoryTests {
	
	@Autowired
	ResultDiffRepository repository;
	
	@BeforeAll
	private void setUp() {
		
		ResultDiff result = new ResultDiff();
		result.setMessage(null);
		result.setResult(ResultDiffEnum.EQUAL);
		result.setDiff(null);
		
		repository.save(result);
	}
	
	@AfterAll
	private void tearDown() {
		repository.deleteAll();
	}
	
	@Test
	public void testSave() {
		
		ResultDiff result = new ResultDiff();
		result.setMessage(null);
		result.setResult(ResultDiffEnum.DIFFERENT);
		result.setDiff(null);
		
		repository.save(result);
		
		assertNotNull(result);
	}
	
	@Test
	public void testFindById(){
		Optional<ResultDiff> response = repository.findById(2L);
		
		assertTrue(response.isPresent());
		assertEquals(2L, response.get().getId());
	}

}
