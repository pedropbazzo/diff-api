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

import io.github.mariazevedo88.diffapi.model.ResultDiff;
import io.github.mariazevedo88.diffapi.repository.diff.ResultDiffRepository;
import io.github.mariazevedo88.diffapi.service.ResultDiffService;

@SpringBootTest
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, MockitoTestExecutionListener.class })
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class ResultDiffServiceTests {
	
	@MockBean
	ResultDiffRepository repository;
	
	@Autowired
	ResultDiffService service;
	
	@BeforeAll
	public void setUp() {
		BDDMockito.given(repository.findById(Mockito.anyLong()))
			.willReturn(Optional.of(new ResultDiff()));
	}
	
	@Test
	public void testFindById() {
		Optional<ResultDiff> result = service.findById(1L);
		assertTrue(result.isPresent());
	}

}
