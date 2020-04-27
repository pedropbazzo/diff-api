package io.github.mariazevedo88.diffapi.service.diff;

import java.util.List;
import java.util.Optional;

import io.github.mariazevedo88.diffapi.dto.model.diff.MessageDiffDTO;
import io.github.mariazevedo88.diffapi.model.diff.MessageDiff;
import io.github.mariazevedo88.diffapi.model.message.Message;

public interface MessageDiffService {
	
	MessageDiff saveMessageDiff(MessageDiffDTO messageDiffDTO);
	
	MessageDiff convertDTOToEntity(MessageDiffDTO dto);
	
	MessageDiffDTO convertEntityToDTO(MessageDiff message);
	
	/**
	 * Method that compares two sequences of JSON base64 data and computes the offsets and 
     * lengths of the differences.
     * 
     * @author Mariana Azevedo
	 * @since 25/07/2019
	 * 
     * @param message data came from left endpoint.
     * @return a list of {@code MessageDiff} with offsets and lengths of the differences.
	 */
	MessageDiff checkDiffBetweenWords(Message message);

	Optional<MessageDiff> findById(long id);
	
	List<MessageDiff> findAll();

}
