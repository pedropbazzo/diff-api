package io.github.mariazevedo88.diffapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Class that starts the diff-api application
 * 
 * @author Mariana Azevedo
 * @since 08/03/2020
 *
 */
@SpringBootApplication
public class DiffApiApplication {
	
	private static boolean isExecuted;

	public static void main(String[] args) {
		SpringApplication.run(DiffApiApplication.class, args);
		isExecuted = true;
	}

	public static boolean isExecuted() {
		return isExecuted;
	}
}
