package io.github.mariazevedo88.diffapi.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Response<T> {

	private T data;
	private List<String> errors;
	
	public List<String> getErrors(){
		if(errors == null) {
			this.errors = new ArrayList<>();
		}
		return errors;
	}
}
