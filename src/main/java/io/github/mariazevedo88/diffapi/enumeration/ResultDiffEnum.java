package io.github.mariazevedo88.diffapi.enumeration;

public enum ResultDiffEnum {
	
	EQUAL("EQUAL"),
	DIFFERENT_SIZE("DIFFERENT_SIZE"),
    DIFFERENT("DIFFERENT"),
    NOT_FOUND("NOT_FOUND");
	
	private String value;
	
	private ResultDiffEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
