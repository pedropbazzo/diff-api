package io.github.mariazevedo88.diffapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class that provides simple storage in the RAM memory to JSON base64 data. It was implemented in a Abstract Factory pattern, 
 * just to emulate data storage while the service is running.
 * 
 * @author Mariana Azevedo
 * @since 01/09/2019
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageDTO {
	
	private Long id;
	private String leftData;
	private String rightData;

}
