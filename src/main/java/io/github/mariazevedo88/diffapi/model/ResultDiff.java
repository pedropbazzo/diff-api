package io.github.mariazevedo88.diffapi.model;

import java.io.Serializable;
import java.util.List;

import io.github.mariazevedo88.diffapi.enumeration.ResultDiffEnum;

/**
 * Class that implements a ResultDiff structure with an id, a diff result and the list of the diffs found.
 * 
 * @author Mariana Azevedo
 * @since 25/07/2019
 *
 */
public class ResultDiff implements Serializable{

	private static final long serialVersionUID = 6077656822481760215L;
	
	private Long id;
	
	private ResultDiffEnum result;
	
	private List<MessageDiff> diffs;
	
	public ResultDiff() {}
	
	public ResultDiff(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ResultDiffEnum getResult() {
		return result;
	}

	public void setResult(ResultDiffEnum result) {
		this.result = result;
	}

	public List<MessageDiff> getDiffs() {
		return diffs;
	}

	public void setDiffs(List<MessageDiff> diffs) {
		this.diffs = diffs;
	}

}
