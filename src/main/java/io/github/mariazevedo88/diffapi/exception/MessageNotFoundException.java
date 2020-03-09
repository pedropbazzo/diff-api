package io.github.mariazevedo88.diffapi.exception;

public class MessageNotFoundException extends Exception {

	private static final long serialVersionUID = 678170836636542018L;
	
	public MessageNotFoundException(String msg){
		super(msg);
	}
	
	public MessageNotFoundException(String msg, Throwable cause){
		super(msg, cause);
	}

}
