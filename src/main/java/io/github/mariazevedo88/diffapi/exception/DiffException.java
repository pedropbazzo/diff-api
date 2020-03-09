package io.github.mariazevedo88.diffapi.exception;

public class DiffException extends Exception {

	private static final long serialVersionUID = -3974541817505020379L;
	
	public DiffException(String msg){
		super(msg);
	}
	
	public DiffException(String msg, Throwable cause){
		super(msg, cause);
	}

}
