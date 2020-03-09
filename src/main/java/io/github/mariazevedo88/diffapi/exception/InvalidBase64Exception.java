package io.github.mariazevedo88.diffapi.exception;

public class InvalidBase64Exception extends Exception {

	private static final long serialVersionUID = -2616024929398956102L;
	
	public InvalidBase64Exception() {
		super("Invalid Json Base64 supplied in request");
	}
	
	public InvalidBase64Exception(String msg){
		super(msg);
	}
	
	public InvalidBase64Exception(String msg, Throwable cause){
		super(msg, cause);
	}

}
