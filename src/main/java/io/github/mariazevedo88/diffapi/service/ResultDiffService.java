package io.github.mariazevedo88.diffapi.service;

import java.util.Optional;

import io.github.mariazevedo88.diffapi.dto.ResultDiffDTO;
import io.github.mariazevedo88.diffapi.model.Message;
import io.github.mariazevedo88.diffapi.model.ResultDiff;

public interface ResultDiffService {
	
	ResultDiff saveResultDiff(ResultDiffDTO resultDiffDTO);
	
	ResultDiff convertDTOToEntity(ResultDiffDTO dto);
	
	ResultDiffDTO convertEntityToDTO(ResultDiff message);

	/**
	 * Method that compares two data sequences according to an informed id. The comparison only happens if both 
	 * messages/inputs (left and right) are found for the same id.
	 * 
	 * @author Mariana Azevedo
	 * @since 25/07/2019
	 * @param id
	 * @return <p>Null if both or at least one input (left or right) are null.
     * <p>ResultDiff with all comparison data.
	 */
	ResultDiff compare (Message message);

	Optional<ResultDiff> findById(long id);
	
}
