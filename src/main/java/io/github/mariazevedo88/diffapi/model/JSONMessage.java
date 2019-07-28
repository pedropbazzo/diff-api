package io.github.mariazevedo88.diffapi.model;

/**
 * Class that implements a JSONMessage structure with an id and an value.
 * 
 * @author Mariana Azevedo
 * @since 23/07/2019
 * 
 */
public class JSONMessage {
	
	private Long id;
	
	private String value;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "JSONMessage [id=" + id + ", value=" + value + "]";
	}
	
}
