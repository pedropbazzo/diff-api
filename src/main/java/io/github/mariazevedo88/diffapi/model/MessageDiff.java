package io.github.mariazevedo88.diffapi.model;

import java.io.Serializable;

/**
 * Class that implements a MessageDiff structure with an offset and a length.
 * 
 * @author Mariana Azevedo
 * @since 25/07/2019
 * 
 */
public class MessageDiff implements Serializable{

	private static final long serialVersionUID = 190102684474375198L;
	
	private int offset;
	private int length;
	
	public MessageDiff() {}
	
	public MessageDiff(int offset, int length) {
		this.offset = offset;
		this.length = length;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + length;
		result = prime * result + offset;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MessageDiff other = (MessageDiff) obj;
		if (length != other.length)
			return false;
		if (offset != other.offset)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MessageDiff [offset=" + offset + ", length=" + length + "]";
	}

}
