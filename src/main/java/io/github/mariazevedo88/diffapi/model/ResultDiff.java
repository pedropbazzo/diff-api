package io.github.mariazevedo88.diffapi.model;

import java.io.Serializable;
import java.util.List;

import io.github.mariazevedo88.diffapi.enumeration.ResultDiffEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class that implements a ResultDiff structure with an id, a diff result and the list of the diffs found.
 * 
 * @author Mariana Azevedo
 * @since 25/07/2019
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResultDiff implements Serializable{

	private static final long serialVersionUID = 6077656822481760215L;
	
	private Long id;
	private ResultDiffEnum result;
	private List<MessageDiff> diffs;
	
	public ResultDiff(Long id) {
		this.id = id;
	}

}
