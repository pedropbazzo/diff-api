package io.github.mariazevedo88.diffapi.enumeration;

/**
 * Enum that classifies the types of comparison's results that ComparatorService provides.
 * 
 * @author Mariana Azevedo
 * @since 25/07/2019
 *
 */
public enum ResultDiffEnum {
	
	EQUAL("EQUAL"),
	DIFFERENT_SIZE("DIFFERENT_SIZE"),
    DIFFERENT("DIFFERENT");
	
	private String value;
	
	private ResultDiffEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
