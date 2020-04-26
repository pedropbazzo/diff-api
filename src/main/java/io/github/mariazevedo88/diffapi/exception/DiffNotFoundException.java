package io.github.mariazevedo88.diffapi.exception;

public class DiffNotFoundException extends Exception {

	private static final long serialVersionUID = 2451874043155202258L;
	
	public DiffNotFoundException(String msg){
		super(msg);
	}
	
	public DiffNotFoundException(String msg, Throwable cause){
		super(msg, cause);
	}

}
