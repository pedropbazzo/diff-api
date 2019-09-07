package io.github.mariazevedo88.diffapi.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class that implements a MessageDiff structure with an offset and a length.
 * 
 * @author Mariana Azevedo
 * @since 25/07/2019
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class MessageDiff implements Serializable{

	private static final long serialVersionUID = 190102684474375198L;
	
	private int offset;
	private int length;
	
	@Override
	public String toString() {
		return "MessageDiff [offset=" + offset + ", length=" + length + "]";
	}

}
