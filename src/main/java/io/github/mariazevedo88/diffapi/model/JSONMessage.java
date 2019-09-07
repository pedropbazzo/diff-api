package io.github.mariazevedo88.diffapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class that implements a JSONMessage structure with an id and an value.
 * 
 * @author Mariana Azevedo
 * @since 23/07/2019
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JSONMessage {
	
	private Long id;
	private String value;
	
	@Override
	public String toString() {
		return "JSONMessage [id=" + id + ", value=" + value + "]";
	}
	
}
