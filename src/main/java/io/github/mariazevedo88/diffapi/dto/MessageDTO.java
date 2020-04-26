package io.github.mariazevedo88.diffapi.dto;

import org.springframework.hateoas.RepresentationModel;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Class that provides simple storage in the RAM memory to JSON base64 data. It was implemented in a Abstract Factory pattern, 
 * just to emulate data storage while the service is running.
 * 
 * @author Mariana Azevedo
 * @since 01/09/2019
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class MessageDTO extends RepresentationModel<MessageDTO> {
	
	private Long id;
	
	private String leftData;
	
	private String rightData;

}
